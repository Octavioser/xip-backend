package com.red.xip.shop.controller;

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
    		LOG.info("selectDetailAccount 유저 정보 주소포함 ");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
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
    		LOG.info("updateAccountInfoNm  유저이름 업데이트");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
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
    		LOG.info("updateAccountInfoPw 유저 비번 업데이트");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		
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
    		LOG.info("insertAdd 유저 주소 등록 및 업데이트");

    		String userCd = (String) servletRequest.getAttribute("userCd");

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
			String realIp = servletRequest.getHeader("X-Forwarded-For");
    		if (realIp == null) {
    		    realIp = servletRequest.getRemoteAddr();
    		}
    		LOG.info("클라이언트 IP: {}", realIp);
			LOG.info("selectProdList 제품들 조회" + param.getStatus());
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
			LOG.info("selectDetailProdList 상세 제품 조회 " + param.getProdCd());
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
    		LOG.info("insertCart 제품 상세페이지에서 장바구니 담기");

    		String userCd = (String) servletRequest.getAttribute("userCd");

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
    		LOG.info("selectCart 장바구니 조회");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("updateCartQty 장바구니 수량 변경");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("deleteWebauthn 생체인증 정보 삭제");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("deleteAccount 회원 삭제");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("selectOrder 주문 조회");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("selectOrderDetails 상세 주문 조회");

    		String userCd = (String) servletRequest.getAttribute("userCd");

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
    		LOG.info("updateCancelOrder 주문취소신청");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
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
    		LOG.info("updateCancellingCancel 주문취소를 취소");

    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
            param.setUserCd(userCd);
            
            return APIResult.success(service.updateCancellingCancel(param));
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCountries
	@PostMapping("/shopR007")
	@ResponseBody
	public APIResult selectCountries(HttpServletRequest servletRequest, HttpServletResponse servletResponse
		/* RequestContext session , */) throws Exception {
    	try {
    		LOG.info("selectCountries 국가조회");
            
            return APIResult.success(service.selectCountries());
            
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
}
