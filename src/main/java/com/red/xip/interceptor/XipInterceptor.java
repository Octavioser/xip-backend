package com.red.xip.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.red.xip.common.SecurityConstants;
import com.red.xip.util.model.APIResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class XipInterceptor implements HandlerInterceptor {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private ObjectMapper objectMapper = new ObjectMapper(); // 객체를 JSON으로 변환하기 위한 ObjectMapper 인스턴스
	
    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        // 컨트롤러 메서드 실행 전
    	try {
    		
    		String authorizationHeader = servletRequest.getHeader("Authorization");
        	String token = authorizationHeader.substring(7); // "Bearer "를 제외한 토큰 부분을 추출합니다.
    		if ( "undefined".equals(token) || token == null || token.trim().isEmpty()) {
    			String json = objectMapper.writeValueAsString(APIResult.tokenFail());
    			servletResponse.getWriter().write(json);
                return false;
            }
    		// 토큰 검증 후 데이터 가져오기
    		Claims claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_SECRET_KEY).build().parseClaimsJws(token).getBody();
    		
            String userCd = claims.getSubject();              // 유저 번호 꺼내기
            String email = claims.get("email", String.class);  // 유저 이메일 꺼내기
            String roleType = claims.get("roleType", String.class);  // 유저 권한 꺼내기
            
            servletRequest.setAttribute("userCd", userCd);
            servletRequest.setAttribute("email", email);
            servletRequest.setAttribute("roleType", roleType);
            return true; // true 반환 시 계속 진행, false 반환 시 처리 중단
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error("Exception [XIPInterceptor에서 에러]: {}", e.getMessage());
			String json = objectMapper.writeValueAsString(APIResult.tokenFail());
			servletResponse.getWriter().write(json);
			return false;
		}
    	
    }

}
