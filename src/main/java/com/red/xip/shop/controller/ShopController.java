package com.red.xip.shop.controller;

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
import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.P_Cart;
import com.red.xip.shop.model.P_Order;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.service.ShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopService service;
	
	// C100생성 R000출력 U200갱신 D300삭제
	// shopDetailAccount
	@PostMapping("/shopR001")
	@ResponseBody
	public Object selectDetailAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            param.setEmail(email);
            
    		return service.selectDetailAccount(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateAccountInfoNm
	@PostMapping("/shopU201")
	@ResponseBody
	public Object updateAccountInfoNm(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            if( !( CommonUtils.stringIfNull(email).equals(param.getEmail() )) ) { // 이메일과 토큰이메일이 일치하는지 확인
    			return -1;
    		}
            
    		return service.updateAccountInfoNm(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateAccountInfoNm
	@PostMapping("/shopU202")
	@ResponseBody
	public Object updateAccountInfoPw(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            if( !( CommonUtils.stringIfNull(email).equals(param.getEmail() )) ) { // 이메일과 토큰이메일이 일치하는지 확인
    			return -1;
    		}
            int result = service.updateAccountInfoPw(param);
            
            if(result < 1) {
            	return -1;
            }
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
		
	// C100생성 R000출력 U200갱신 D300삭제
	// insertAdd
	@PostMapping("/shopC101")
	@ResponseBody
	public Object insertAdd(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            if( !( CommonUtils.stringIfNull(email).equals(param.getEmail() )) ) { // 이메일과 토큰이메일이 일치하는지 확인
    			return -1;
    		}
            
            int result = service.insertAdd(param);
            
            if(result < 1) {
            	return -1;
            }
            
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectProdList
	@PostMapping("/shopR002")
	@ResponseBody
	public Object selectProdList(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
    	return service.selectProdList(param);
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectDetailProdList
	@PostMapping("/shopR003")
	@ResponseBody
	public Object selectDetailProdList(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
    	return service.selectDetailProdList(param);
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// insertAdd
	@PostMapping("/shopC102")
	@ResponseBody
	public Object insertCart(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            int result = service.insertCart(param);
            
            if(result < 1) {
            	return -1;
            }
            
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCart
	@PostMapping("/shopR004")
	@ResponseBody
	public Object selectCart(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cart param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            return service.selectCart(param);
            
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateCartQty
	@PostMapping("/shopU203")
	@ResponseBody
	public Object updateCartQty(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cart param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            return service.updateCartQty(param);
            
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// deleteWebauthn
	@PostMapping("/shopD301")
	@ResponseBody
	public Object deleteWebauthn(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            return service.deleteWebauthn(param);
            
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// deleteAccount
	@PostMapping("/shopD302")
	@ResponseBody
	public Object deleteAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            return service.deleteAccount(param);
            
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCart
	@PostMapping("/shopR005")
	@ResponseBody
	public Object selectOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Order param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
            return service.selectOrder(param);
            
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
}
