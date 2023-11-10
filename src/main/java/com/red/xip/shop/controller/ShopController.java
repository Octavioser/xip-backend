package com.red.xip.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.red.xip.common.SecurityConstants;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.service.ShopService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopService service;
	
	// 이메일 있는지 체크
	@PostMapping("/shopR001")
	@ResponseBody
	public Object selectDetailAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
    	try {
    		String authorizationHeader = servletRequest.getHeader("Authorization");
    		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return -2;
            }
    		String token = authorizationHeader.substring(7); // "Bearer "를 제외한 토큰 부분을 추출합니다.
    		
    		Claims claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_SECRET_KEY).build().parseClaimsJws(token).getBody();
    		
            String user_cd = claims.getSubject();              // 유저 번호 꺼내기
            String email = claims.get("email", String.class);  // 유저 이메일 꺼내기
            
            param.setUserCd(user_cd);
            param.setEmail(email);
            
    		return service.selectDetailAccount(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
}
