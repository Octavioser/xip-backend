package com.red.xip.shop.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.red.xip.shop.model.P_OrderD;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.service.ShopService;
import com.red.xip.util.model.APIResult;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopService service;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	// C100생성 R000출력 U200갱신 D300삭제
	// shopDetailAccount
	@PostMapping("/shopR001")
	@ResponseBody
	public APIResult selectDetailAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            param.setEmail(email);
            
    		return APIResult.success(service.selectDetailAccount(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateAccountInfoNm
	@PostMapping("/shopU201")
	@ResponseBody
	public APIResult updateAccountInfoNm(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            param.setEmail(email);
            
    		return APIResult.success(service.updateAccountInfoNm(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateAccountInfoNm
	@PostMapping("/shopU202")
	@ResponseBody
	public APIResult updateAccountInfoPw(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            param.setEmail(email);
            
    		return APIResult.success(service.updateAccountInfoPw(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
		
	// C100생성 R000출력 U200갱신 D300삭제
	// insertAdd
	@PostMapping("/shopC101")
	@ResponseBody
	public APIResult insertAdd(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);

    		return APIResult.success(service.insertAdd(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectProdList
	@PostMapping("/shopR002")
	@ResponseBody
	public APIResult selectProdList(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
		try {
			CommonUtils.ipLog(servletRequest);
	    	return APIResult.success(service.selectProdList(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectDetailProdList
	@PostMapping("/shopR003")
	@ResponseBody
	public APIResult selectDetailProdList(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
		try {
			return APIResult.success(service.selectDetailProdList(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
    	
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// insertAdd
	@PostMapping("/shopC102")
	@ResponseBody
	public APIResult insertCart(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shop param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);

    		return APIResult.success(service.insertCart(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCart
	@PostMapping("/shopR004")
	@ResponseBody
	public APIResult selectCart(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cart param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.selectCart(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateCartQty
	@PostMapping("/shopU203")
	@ResponseBody
	public APIResult updateCartQty(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cart param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.updateCartQty(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// deleteWebauthn
	@PostMapping("/shopD301")
	@ResponseBody
	public APIResult deleteWebauthn(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.deleteWebauthn(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// deleteAccount
	@PostMapping("/shopD302")
	@ResponseBody
	public APIResult deleteAccount(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Account param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.deleteAccount(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCart
	@PostMapping("/shopR005")
	@ResponseBody
	public APIResult selectOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Order param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.selectOrder(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCart
	@PostMapping("/shopR006")
	@ResponseBody
	public APIResult selectOrderDetails(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_OrderD param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.selectOrderDetails(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateCartQty
	@PostMapping("/shopU204")
	@ResponseBody
	public APIResult updateCancelOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_OrderD param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.updateCancelOrder(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateCartQty
	@PostMapping("/shopU205")
	@ResponseBody
	public APIResult updateCancellingCancel(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_OrderD param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.updateCancellingCancel(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
}
