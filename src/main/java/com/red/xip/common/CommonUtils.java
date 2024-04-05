package com.red.xip.common;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class CommonUtils {
	
	public static HashMap<String, String> getUserInfoFromCookie (HttpServletRequest servletRequest) {
		
		HashMap<String, String> userItem = new HashMap<String, String>();
		try {
			String authorizationHeader = servletRequest.getHeader("Authorization");
    		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
    			userItem.put("userCd", "");
                return userItem;
            }
    		String token = authorizationHeader.substring(7); // "Bearer "를 제외한 토큰 부분을 추출합니다.
    		
    		// 토큰 검증 후 데이터 가져오기
    		Claims claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_SECRET_KEY).build().parseClaimsJws(token).getBody();
    		
            String userCd = claims.getSubject();              // 유저 번호 꺼내기
            String email = claims.get("email", String.class);  // 유저 이메일 꺼내기
            String roleType = claims.get("roleType", String.class);  // 유저 권한 꺼내기
            
            userItem.put("userCd", userCd);
            userItem.put("email", email);
            userItem.put("roleType", roleType);
            
            return userItem;
            
		} catch (Exception e) {
			// TODO: handle exception
			userItem.put("userCd", "");
            return userItem;
		}
	}
	
	public static String stringIfNull(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        return str;
    }
}
