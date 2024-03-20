package com.red.xip.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.red.xip.login.model.P_Login;
import com.red.xip.login.model.P_WebAuth;
import com.red.xip.login.service.LoginService;
import com.red.xip.util.model.APIResult;

@RestController
@RequestMapping("/login")
//@CrossOrigin("http://localhost:3000")
//@CrossOrigin("http://172.30.1.53:3000/")
public class LoginController {

	// C100생성 R000출력 U200갱신 D300삭제
	@Autowired
	LoginService service;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	// 로그인
	@PostMapping("/loginR001")
	@ResponseBody
	public APIResult getLoginCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.getLoginCheck(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// 이메일 있는지 체크
	@PostMapping("/loginR002")
	@ResponseBody
	public APIResult selectEmailCheckAuthCode(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.selectEmailCheckAuthCode(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// 인증코드 체크
	@PostMapping("/loginR003")
	@ResponseBody
	public APIResult selectEmailAuthCodeCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.selectEmailAuthCodeCheck(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// 회원가입
	@PostMapping("/loginC101")
	@ResponseBody
	public APIResult insertCreateAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.insertCreateAccount(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// selectWebAuthCheck  가입이 되어있는지 아니면 등록이 되어있는지
	@PostMapping("/loginR004")
	@ResponseBody
	public APIResult selectWebAuthCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_WebAuth param) throws Exception {
    	try {
    		return APIResult.success(service.selectWebAuthCheck(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// selectWebAuthCheck  가입이 되어있는지 아니면 등록이 되어있는지
	@PostMapping("/loginR005")
	@ResponseBody
	public APIResult selectWebAuthCreateItem(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_WebAuth param) throws Exception {
    	try {
    		return APIResult.success(service.selectWebAuthCreateItem(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// updateWebAuthCheck  가입이 되어있는지 아니면 등록이 되어있는지
	@PostMapping("/loginU201")
	@ResponseBody
	public APIResult updateSaveWebAuth(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_WebAuth param) throws Exception {
    	try {
    		return APIResult.success(service.updateSaveWebAuth(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// selectWebAuthLoginCheck  webAuth로 로그인 한거 검증
	@PostMapping("/loginR006")
	@ResponseBody
	public APIResult selectWebAuthLoginCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_WebAuth param) throws Exception {
    	try {
    		return APIResult.success(service.selectWebAuthLoginCheck(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// 이메일 있는지 체크
	@PostMapping("/loginR007")
	@ResponseBody
	public APIResult selectEmailCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_WebAuth param) throws Exception {
    	try {
    		return APIResult.success(service.selectEmailCheck(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	@PostMapping("/loginR008")
	@ResponseBody
	public APIResult selectForgotPwAuthCode(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.selectForgotPwAuthCode(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// updatePw  비밀번호변경
	@PostMapping("/loginU202")
	@ResponseBody
	public APIResult updatePw(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Login param) throws Exception {
    	try {
    		return APIResult.success(service.updatePw(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
}
