package com.red.xip.xipengineering.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.red.xip.common.CommonUtils;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.P_XLogin;
import com.red.xip.xipengineering.service.XipengineeringService;

@RestController
@RequestMapping("/xipengineering")
public class XipengineeringController {

	@Autowired
	XipengineeringService service;
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectIncuCheck  유저정보들 갖고오기
	@PostMapping("/incuR001")
	@ResponseBody
	public Object selectIncuCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_XLogin param) throws Exception {
		try {
			// 쿠키 정보 갖고오기
			HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
	        
			String userCd = userInfo.get("userCd");
			
			String email = userInfo.get("email");
			
			String roleType = userInfo.get("roleType");
			
			if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
				return -2;
			}
			
			if(!"X".equals(roleType)) {
				return -2;
			}
			
			param.setEmail(email);
			param.setUserCd(userCd);
			
			return 1; 
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectUsers  회원조회
	@PostMapping("/incuR002")
	@ResponseBody
	public Object selectUsers(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_User param) throws Exception {
		try {
			// 쿠키 정보 갖고오기
			HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
	        
			String userCd = userInfo.get("userCd");
			
			String email = userInfo.get("email");
			
			String roleType = userInfo.get("roleType");
			
			if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
				return -2;
			}
			
			if(!"X".equals(roleType)) {
				return -2;
			}
			
			param.setEmail(email);
			param.setUserCd(userCd);
			
			return service.selectUsers(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectOrders  주문내역조회
	@PostMapping("/incuR003")
	@ResponseBody
	public Object selectOrders(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Orders param) throws Exception {
		try {
			// 쿠키 정보 갖고오기
			HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
	        
			String userCd = userInfo.get("userCd");
			
			String email = userInfo.get("email");
			
			String roleType = userInfo.get("roleType");
			
			if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
				return -2;
			}
			
			if(!"X".equals(roleType)) {
				return -2;
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return service.selectOrders(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
}
