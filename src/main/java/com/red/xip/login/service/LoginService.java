package com.red.xip.login.service;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.common.CommonUtils;
import com.red.xip.common.SecurityConstants;
import com.red.xip.login.mapper.LoginMapper;
import com.red.xip.login.model.P_Login;
import com.red.xip.login.model.P_WebAuth;
import com.red.xip.login.model.R_Login;
import com.red.xip.login.model.R_WebAuth;

import com.webauthn4j.data.AuthenticationData;
import com.webauthn4j.data.AuthenticationParameters;
import com.webauthn4j.data.AuthenticationRequest;
import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.authenticator.COSEKey;
import com.webauthn4j.data.attestation.authenticator.Curve;
import com.webauthn4j.data.attestation.authenticator.EC2COSEKey;
import com.webauthn4j.data.attestation.authenticator.RSACOSEKey;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.validator.exception.ValidationException;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.authenticator.AuthenticatorImpl;

import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

@Service
public class LoginService {

	@Autowired
	LoginMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AwsSesService awsSesService;
	
	@Autowired // 이메일템플릿
    private TemplateEngine templateEngine;
	
	@Value("${app.origin}")
    private String appOrigin;
	
	@Value("${app.rpId}")
    private String appRpId;
	
	private final WebAuthnManager webAuthnManager = WebAuthnManager.createNonStrictWebAuthnManager();

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public List<R_Login> getLoginCheck(P_Login param) throws Exception {
		try {
			List<R_Login> resultData = mapper.getLoginCheck(param);

			if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
				Date now = new Date();
				String token = Jwts.builder()
						.setSubject(resultData.get(0).getUserCd())       // 토큰의 주제 설정 (예: 사용자 ID)
						.claim("email", resultData.get(0).getEmail())     // 사용자 이메일 추가
						.claim("roleType", resultData.get(0).getRoleType())     // 사용자 권한추가 'U'
						.setIssuedAt(now)                                // 토큰 발급일
						.setExpiration(new Date(now.getTime() + Duration.ofHours(12).toMillis())) // 토큰 만료일
					    .signWith(SecurityConstants.JWT_SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
					    .compact();	
				resultData.get(0).setToken(token);    // 토큰넣어주기
				
				resultData.get(0).setPw(null);
				resultData.get(0).setUserCd(null);
				
				return resultData;
			}
			else {
				return Collections.emptyList();
			}
			
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	// 비회원이메일 체크 후 인증코드
	@Transactional(rollbackFor = Exception.class)
	public int selectEmailCheckAuthCode(P_Login param) throws Exception {
		try {
			Random random = new Random();
			
			int count = mapper.selectEmailCheck(param);
			if(count == 0) {
				int createNum;
				String ranNum = "";
				String resultNum = "";
				for (int i=0; i<6; i++) { 
					createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
					ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
					resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
				}
				
				
				param.setAuthCd(resultNum);
				
				mapper.insertAuthCd(param);
				
				Context context = new Context();
		        context.setVariable("resultNum", resultNum);

		        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
		        String emailContent = templateEngine.process("emailAuthCdTempl", context);
		        
			    String senderEmail = "xipservice@xip.red";
			    String receiverEmail = param.getEmail();
			    String emailSubject = "XIP account confirmation";
				awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
			}
			return count;
			
		}
		catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	// 회원가입
	@Transactional(rollbackFor = Exception.class)
	public List<R_Login> insertCreateAccount(P_Login param) throws Exception {
		try {
			int checkEmail = mapper.selectEmailCheck(param);
			if(checkEmail > 0) {
				// 계정이 있을 경우
				return Collections.emptyList();
			}
			param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
			int count =	mapper.selectEmailAuthCodeCheck(param);
			if(count == 0) { // 인증코드가 맞지 않을 경우
				return Collections.emptyList();
			}

			mapper.insertCreateAccount(param); // 회원가입 데이터 저장
			
			List<R_Login> resultData = mapper.getLoginCheck(param);
			// 인증 성공시
			Date now = new Date();
			String token = Jwts.builder()
					.setSubject(resultData.get(0).getUserCd())       // 토큰의 주제 설정 (예: 사용자 ID)
					.claim("email", resultData.get(0).getEmail())     // 사용자 이메일 추가
					.claim("roleType", resultData.get(0).getRoleType())     // 사용자 권한추가 'U'
					.setIssuedAt(now)                                // 토큰 발급일
					.setExpiration(new Date(now.getTime() + Duration.ofHours(12).toMillis())) // 토큰 만료일
				    .signWith(SecurityConstants.JWT_SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
				    .compact();	
			resultData.get(0).setToken(token);    // 토큰넣어주기
			resultData.get(0).setPw(null);
			resultData.get(0).setUserCd(null);
			
			return resultData;
			
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
		
	}

	public int selectEmailAuthCodeCheck(P_Login param) throws Exception {
		// TODO Auto-generated method stub
		try {
			int count =	mapper.selectEmailAuthCodeTimeCheck(param); // 제한시간안에 인증코드
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			return -1;
		}
	}

	// 회원가입되어있는지 webAuth등록이 되어있는지 확인 -> 확인되면 생체인증을 위한 값 리턴
	@Transactional(rollbackFor = Exception.class)
	public List<R_WebAuth> selectWebAuthCheck(P_WebAuth param) throws Exception {
		try {
			List<R_WebAuth> result = mapper.selectWebAuthItem(param);
			
			if(!result.isEmpty() && result.size() > 0) {

				// 가입입된 이메일이랑 webauth 등록했는지
				if(!"".equals(CommonUtils.stringIfNull(result.get(0).getEmail())) && 
						!"".equals(CommonUtils.stringIfNull(result.get(0).getWebAuthId()))
				) {
					SecureRandom random = new SecureRandom();
			        byte[] bytes = new byte[32]; // 32바이트의 무작위 데이터 생성
			        random.nextBytes(bytes);
			     // challange변수 생성
			        String challenge = Base64.getUrlEncoder().encodeToString(bytes);
			        result.get(0).setChallenge( challenge); // URL-safe Base64 인코딩
			        
			        // 챌린지값 저장
			        param.setChallenge(challenge);
			        param.setUserCd(result.get(0).getUserCd()); 
			        
			        mapper.updateChallenge(param);
				}
				result.get(0).setUserCd(null);
				result.get(0).setPw(null);
				result.get(0).setPk(null);
				result.get(0).setAaguid(null);
				result.get(0).setRoleType(null);
				
				return result;
			}
			else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
		
	}

	// 회원인지 확인 후 생체인증등록에 필요한 값 리턴
	@Transactional(rollbackFor = Exception.class)
	public List<R_WebAuth> selectWebAuthCreateItem(P_WebAuth param) throws Exception {
		try {
			P_Login param2 = new P_Login();
			
			param2.setEmail(param.getEmail());
			
			List<R_Login> resultData = mapper.getLoginCheck(param2);
	
			// 패스워드가 맞는 지 확인
			if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
				
				List<R_WebAuth> result = mapper.selectWebAuthItem(param);
	
				// 회원가입이 되어있는지 확인
				if(!"".equals(CommonUtils.stringIfNull(result.get(0).getEmail()))) {
					SecureRandom random = new SecureRandom();
			        byte[] bytes = new byte[32]; // 32바이트의 무작위 데이터 생성
			        random.nextBytes(bytes);
			        
			        // challange변수 생성
			        String Challenge = Base64.getUrlEncoder().encodeToString(bytes);
			        result.get(0).setChallenge(Challenge);
					result.get(0).setUserIdBase64( // userIdBuffer 생성
						Base64.getEncoder().encodeToString(result.get(0).getUserCd().getBytes())
					); 
					
					// challange 저장하기
			        param.setChallenge(Challenge);
			        
			        mapper.createWebAuthnSaveChallenge(param); // 챌린지 값이외에 null값으로 변경(초기화)
				}
				else {
					return Collections.emptyList();
				}
	
				result.get(0).setUserCd(null);
				result.get(0).setPw(null);
				result.get(0).setPk(null);
				result.get(0).setAaguid(null);
				result.get(0).setRoleType(null);
	
				return result;
	
			}
			else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	// 생체인증 등록
	@Transactional(rollbackFor = Exception.class)
	public int updateSaveWebAuth(P_WebAuth param) throws Exception {
		// TODO Auto-generated method stub
		try {
			// 클라이언테 데이터
			byte[] clientDataBytes = Base64.getDecoder().decode (param.getClientDataJSON());
			byte[] attestationObjectData = Base64.getDecoder().decode(param.getAttestationObject());

			// clientDataJSON
			String clientDataString = new String(clientDataBytes);
	        
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(clientDataString);
	        String origin = jsonObject.get("origin").toString();
	        String type = jsonObject.get("type").toString();
	        String challenge = jsonObject.get("challenge").toString();
	        
	        List<R_WebAuth> result = mapper.selectWebAuthItem(param);
	        
	        // challenge 꺼내오기
	        String savedChallenge = CommonUtils.stringIfNull(result.get(0).getChallenge()).replaceAll("=", "");
	        
	        challenge = challenge.replaceAll("=", "");
	       
	        
	        // CBOR 파싱을 위한 ObjectMapper 준비
	        CBORFactory factory = new CBORFactory();
	        ObjectMapper cborMapper = new ObjectMapper(factory);
	        
	        // CBOR 데이터 파싱
	        @SuppressWarnings("unchecked")
			Map<String, Object> attestationObject = cborMapper.readValue(attestationObjectData, Map.class);
	        
	        // authData 추출
	        byte[] authData = (byte[]) attestationObject.get("authData");
	        
	        // aaguid 추출 (authData의 처음 32바이트를 넘어서는 부분에서 16바이트)
	        byte[] aaguid = new byte[16];
	        System.arraycopy(authData, 32, aaguid, 0, 16);
	        
	        param.setAaguid(Base64.getEncoder().encodeToString(aaguid));
	        
	        // Credential ID의 길이를 추출 (AAGUID 다음의 2바이트)
	        int credentialIdLength = ((authData[53] & 0xFF) << 8) | (authData[54] & 0xFF);

	        // Credential ID를 추출
	        byte[] credentialId = new byte[credentialIdLength];
	        System.arraycopy(authData, 55, credentialId, 0, credentialIdLength);
	        
	        param.setWebAuthId(Base64.getEncoder().encodeToString(credentialId));

			//  clientData 맞는지 확인
	        if("webauthn.create".equals(type) && appOrigin.equals(origin) && savedChallenge.equals(challenge)) {
	        	param.setUserCd(result.get(0).getUserCd());
	        	return mapper.updateSaveWebAuth(param);
	        }
	        
	        return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	// 생체인증 후 검증
	public List<R_WebAuth> selectWebAuthLoginCheck(P_WebAuth param) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			List<R_WebAuth> result = mapper.selectWebAuthItem(param);
			
			byte[] authenticatorData =Base64.getDecoder().decode(param.getAuthenticatorData());
			byte[] clientDataJSON = Base64.getDecoder().decode(param.getClientDataJSON());
			byte[] signature =Base64.getDecoder().decode(param.getSignature());
			byte[] userHandle =Base64.getDecoder().decode(param.getUserHandle());
			
			byte[] credentialId = Base64.getDecoder().decode(result.get(0).getWebAuthId());
			String challengeData = result.get(0).getChallenge();
			byte[] aaguid = Base64.getDecoder().decode(result.get(0).getAaguid());
			byte[] pk = Base64.getDecoder().decode(result.get(0).getPk());
			String pkAlgo = result.get(0).getPkAlgo();
			
			Challenge challenge = new DefaultChallenge(challengeData);
			
			Origin origin = new Origin(appOrigin);
			
			// 서버 프로퍼티 설정
	        ServerProperty serverProperty = new ServerProperty(origin, appRpId, challenge, null);
	        
	        
	     // PublicKey 객체 생성
	        X509EncodedKeySpec spec = new X509EncodedKeySpec(pk);
	        
	        COSEKey coseKey = null;
	        
	        // COSEKey 인스턴스 생성
	        // 여기서는 EC2COSEKey를 예로 들었지만, 실제 키 유형에 맞는 COSEKey 구현체를 사용해야 함
	        if("-257".equals(pkAlgo)) {
	        	KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // 예시로 "EC" 사용. 실제 유형에 따라 다를 수 있음
	        	PublicKey publicKey = keyFactory.generatePublic(spec);
	        	 // RSAPublicKey로 캐스팅
	            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

	            // 모듈러스(n)와 지수(e) 추출
	            byte[] modulus = rsaPublicKey.getModulus().toByteArray();
	            byte[] exponent = rsaPublicKey.getPublicExponent().toByteArray();
	            
	            
	        	coseKey = new RSACOSEKey(null, COSEAlgorithmIdentifier.RS256, null,  modulus, exponent);
	        }
	        else if("-7".equals(pkAlgo)) {
	        	// KeyFactory를 사용하여 PublicKey 생성
	            KeyFactory keyFactory = KeyFactory.getInstance("EC");
	            PublicKey publicKey = keyFactory.generatePublic(spec);
	            
	            // ECPublicKey로 캐스팅
	            ECPublicKey ecPublicKey = (ECPublicKey) publicKey;

	            // x와 y 좌표 추출
	            byte[] x = ecPublicKey.getW().getAffineX().toByteArray();
	            byte[] y = ecPublicKey.getW().getAffineY().toByteArray();
	            
	            coseKey = new EC2COSEKey(null, COSEAlgorithmIdentifier.ES256, null, Curve.SECP256R1, x, y);
	        }
	        
	        AttestedCredentialData attestedCredentialData = new AttestedCredentialData(new AAGUID(aaguid), credentialId, coseKey);
	        
	        // 사용자의 인증장치 로드 (등록 과정에서 저장된 데이터)
	        Authenticator authenticator = new AuthenticatorImpl( // You may create your own Authenticator implementation to save friendly authenticator name 
	        		attestedCredentialData, 
	        		null, 
	        		0
	        );
	        

	        // WebAuthn 인증 요청 객체 생성
	        AuthenticationRequest authenticationRequest = 
	        		new AuthenticationRequest(
	        				credentialId,
	        				userHandle,
	        				authenticatorData,
	        				clientDataJSON,
	        				signature
	        		);
	     // 인증 매개변수 설정
	        AuthenticationParameters authenticationParameters = new AuthenticationParameters(
	                serverProperty, 
	                authenticator, 
	                null,
	                true
	        );	
	        // 인증 요청 객체 생성
	        AuthenticationData authenticationData = null;
			try {
				authenticationData = webAuthnManager.parse(authenticationRequest);
			} catch (Exception e) {
				LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
				throw e;
			}	
			// 인증
			try {
			    webAuthnManager.validate(authenticationData, authenticationParameters);
			} catch (ValidationException e) {
				// 인증 실패
				LOG.error("ValidationException [Err_Location] : {}", e.getStackTrace()[0]);
				throw e;
			}
			
			// 인증 성공시
			Date now = new Date();
			String token = Jwts.builder()
					.setSubject(result.get(0).getUserCd())       // 토큰의 주제 설정 (예: 사용자 ID)
					.claim("email", result.get(0).getEmail())     // 사용자 이메일 추가
					.claim("roleType", result.get(0).getRoleType())     // 사용자 권한추가
					.setIssuedAt(now)                                // 토큰 발급일
					.setExpiration(new Date(now.getTime() + Duration.ofHours(12).toMillis())) // 토큰 만료일
				    .signWith(SecurityConstants.JWT_SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
				    .compact();	
			result.get(0).setToken(token);    // 토큰넣어주기
			result.get(0).setUserCd(null);
			result.get(0).setPw(null);
			result.get(0).setPk(null);
			result.get(0).setAaguid(null);
			result.get(0).setRoleType(null);
			
			return result;
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	// 이메일 체크 생체인증 등록되있는지도 확인
	@Transactional(rollbackFor = Exception.class)
	public List<R_WebAuth> selectEmailCheck(P_WebAuth param) throws Exception {
		try {
			
			List<R_WebAuth> resultData = mapper.selectEmailWebAuthCheck(param);
			
			// 회원가입이 되어있느지
			if(!resultData.isEmpty() && resultData.size() > 0) {
				// 가입입된 이메일이랑 webauth 등록했는지
				if(!"".equals(CommonUtils.stringIfNull(resultData.get(0).getEmail())) && 
						!"".equals(CommonUtils.stringIfNull(resultData.get(0).getWebAuthId()))
				) 
				{
					SecureRandom random = new SecureRandom();
			        byte[] bytes = new byte[32]; // 32바이트의 무작위 데이터 생성
			        random.nextBytes(bytes);
			     // challange변수 생성
			        String challenge = Base64.getUrlEncoder().encodeToString(bytes);
			        resultData.get(0).setChallenge( challenge); // URL-safe Base64 인코딩
			        
			        // 챌린지값 저장
			        param.setChallenge(challenge);
			        param.setUserCd(resultData.get(0).getUserCd());
			        
			        resultData.get(0).setUserCd(null);
			        
			        mapper.updateChallenge(param);
				}
				// 등록이 안되어있는 사람 result값 리턴
				resultData.get(0).setUserCd(null);
				return resultData;
			}
			else {
				// 회원가입이 안되어 있는 사람
				return Collections.emptyList();
			}
			
		}
		catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	// 비번업데이트 회원인지 체크 후 인증코드
	@Transactional(rollbackFor = Exception.class)
	public int selectForgotPwAuthCode(P_Login param) throws Exception {
		try {
			Random random = new Random();
			
			int count = mapper.selectEmailCheck(param);
			if(count > 0) {
				int createNum;
				String ranNum = "";
				String resultNum = "";
				for (int i=0; i<6; i++) { 
					createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
					ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
					resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
				}
				
				
				param.setAuthCd(resultNum);
				
				mapper.insertAuthCd(param);
				
				Context context = new Context();
		        context.setVariable("resultNum", resultNum);

		        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
		        String emailContent = templateEngine.process("emailForGotPwAuthCdTempl", context);
		        
			    String senderEmail = "xipservice@xip.red";
			    String receiverEmail = param.getEmail();
			    String emailSubject = "XIP reset your password";
				awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
				
			}
			return count;
			
		}
		catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updatePw(P_Login param) throws Exception {
		try {
			int checkEmail = mapper.selectEmailCheck(param);
			if(checkEmail < 1) {
				return -1;
			}
			
			param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
			
			int count =	mapper.selectEmailAuthCodeCheck(param);
			if(count < 1) {
				return -1;
			}
			
			return mapper.updatePw(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

}
