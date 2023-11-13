package com.red.xip.login.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.common.SecurityConstants;
import com.red.xip.login.mapper.LoginMapper;
import com.red.xip.login.model.P_Login;
import com.red.xip.login.model.R_Login;

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

	
	public List<R_Login> getLoginCheck(P_Login param) {
		try {
			List<R_Login> resultData = mapper.getLoginCheck(param);

			if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
				resultData.get(0).setStatus(1);
				Date now = new Date();
				String token = Jwts.builder()
						.setSubject(resultData.get(0).getUserCd())       // 토큰의 주제 설정 (예: 사용자 ID)
						.claim("email", resultData.get(0).getEmail())     // 사용자 이메일 추가
						.setIssuedAt(now)                                // 토큰 발급일
//						.setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // 토큰 만료일
					    .signWith(SecurityConstants.JWT_SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
					    .compact();	
				resultData.get(0).setToken(token);    // 토큰넣어주기
				return resultData;
			}
			else {
				
				return Collections.emptyList();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// 이메일 체크
	public int selectEmailCheck(P_Login param) throws Exception {
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
//				String emailContent =
//			    		"<!DOCTYPE html>\n" +
//					    "<html lang=\"en\">\n" +
//					    "<head>\n" +
//					    "    <meta charset=\"utf-8\">\n" +
//					    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
//					    "    <title>Example HTML Email</title>\n" +
//					    "</head>\n" +
//					    "<body style=\"background: red; padding: 30px; height: 100%; text-align: center\">\n" +
//					    "    <h5 style=\"color: #FFFFFF; font-size: 40px;\">X I P</h5>\n" +
//					    "    <p></p>\n" +
//					    "    <p style=\"color: #FFFFFF; font-size: 30px; font-weight: 500\">VERIFICATION CODE</p>\n" +
//					    "    <p style=\"color: #FFFFFF; font-size: 20px\">" + resultNum + "</p>\n" + 
//					    "</body>\n" +
//					    "</html>";
				// HTML 파일 읽기
				
				Context context = new Context();
		        context.setVariable("resultNum", resultNum);

		        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
		        String emailContent = templateEngine.process("emailAuthCdTempl", context);
		        
			    String senderEmail = "xipservice@xip.red";
			    String receiverEmail = param.getEmail();
			    String emailSubject = "Customer account confirmation";
				awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
				mapper.insertAuthCd(param);
			}
			return count;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 회원가입
	public Object insertCreateAccount(P_Login param) throws Exception {
		// TODO Auto-generated method stub
		int checkEmail = mapper.selectEmailCheck(param);
		if(checkEmail > 0) {
			// 계정이 있을 경우
			return -1;
		}
		param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
		int count =	mapper.selectEmailAuthCodeCheck(param);
		if(count == 0) {
			return -1;
		}
		return mapper.insertCreateAccount(param);
	}

	public Object selectEmailAuthCodeCheck(P_Login param) {
		// TODO Auto-generated method stub
		try {
			int count =	mapper.selectEmailAuthCodeCheck(param);
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
