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
import com.red.xip.xipengineering.model.P_Canceled;
import com.red.xip.xipengineering.model.P_Cancelling;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_ProdOrder;
import com.red.xip.xipengineering.model.P_PurchaseOrders;
import com.red.xip.xipengineering.model.P_Shipped;
import com.red.xip.xipengineering.model.P_Tracking;
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
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectPurchaseOrder  운송장 등록을 위한 주문내역
	@PostMapping("/incuR004")
	@ResponseBody
	public Object selectPurchaseOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_PurchaseOrders param) throws Exception {
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
			
			return service.selectPurchaseOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectTrackingInfo  운송장 등록 다이얼로그
	@PostMapping("/incuR005")
	@ResponseBody
	public Object selectTracking(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tracking param) throws Exception {
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
			
			return service.selectTracking(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateTrackingNum  운송장 등록 다이얼로그
	@PostMapping("/incuU201")
	@ResponseBody
	public Object updateTrackingNum (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tracking param) throws Exception {
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
			
			return service.updateTrackingNum(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectShipped  발송완료 메뉴
	@PostMapping("/incuR006") 
	@ResponseBody
	public Object selectShipped(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shipped param) throws Exception {
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
			
			return service.selectShipped(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCancelling  취소요청
	@PostMapping("/incuR007") 
	@ResponseBody
	public Object selectCancelling(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
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
			
			return service.selectCancelling(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectDetailCancelling  취소요청 메뉴에서 취소버튼클릭시
	@PostMapping("/incuR008") 
	@ResponseBody
	public Object selectDetailCancelling(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
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
			
			return service.selectDetailCancelling(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateTrackingNum  운송장 등록 다이얼로그
	@PostMapping("/incuU202")
	@ResponseBody
	public Object updateCanceled (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
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
			
			return service.updateCanceled(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCanceled  취소내역
	@PostMapping("/incuR009")
	@ResponseBody
	public Object selectCanceled(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Canceled param) throws Exception {
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
			
			return service.selectCanceled(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectProdOrder  취소내역
	@PostMapping("/incuR010")
	@ResponseBody
	public Object selectProdOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdOrder param) throws Exception {
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
			
			return service.selectProdOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
	
}
