package com.red.xip.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.red.xip.login.model.P_Login;
import com.red.xip.login.service.LoginService;

@RestController
@RequestMapping("/login")
//@CrossOrigin("http://localhost:3000")
//@CrossOrigin("http://172.30.1.53:3000/")
public class LoginController {

	// C100생성 R000출력 U200갱신 D300삭제
	@Autowired
	LoginService service;
	
	// 로그인
	@PostMapping("/loginR001")
	@ResponseBody
	public Object getLoginCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
//    		List<R_Login> result = service.getLoginCheck(param);
//    		Cookie idCookie = new Cookie("memberId", String.valueOf(result.get(0).getId()));
//    		servletResponse.addCookie(idCookie);
//    		System.out.println("쿠키 정보 전달 완료 : "+ idCookie);
//    		HttpSession session = servletRequest.getSession();
//    		return result;
//    		System.out.println("쿠키 정보 전달 완료 : "+ String.valueOf(param));
    		return service.getLoginCheck(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 이메일 있는지 체크
	@PostMapping("/loginR002")
	@ResponseBody
	public Object selectEmailCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return service.selectEmailCheck(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 인증코드 체크
	@PostMapping("/loginR003")
	@ResponseBody
	public Object selectEmailAuthCodeCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return service.selectEmailAuthCodeCheck(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 회원가입
	@PostMapping("/loginC101")
	@ResponseBody
	public Object insertCreateAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return service.insertCreateAccount(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
