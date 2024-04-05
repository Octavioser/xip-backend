package com.red.xip.xipengineering.controller;

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

import com.red.xip.util.model.APIResult;
import com.red.xip.xipengineering.model.P_Canceled;
import com.red.xip.xipengineering.model.P_Cancelling;
import com.red.xip.xipengineering.model.P_NewProd;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_ProdOrder;
import com.red.xip.xipengineering.model.P_ProdStatus;
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
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectIncuCheck  유저정보들 갖고오기
	@PostMapping("/incuR001")
	@ResponseBody
	public APIResult selectIncuCheck(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_XLogin param) throws Exception {
		try {
			LOG.info("selectIncuCheck 관리자 권한");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
    		
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setEmail(email);
			param.setUserCd(userCd);
			
			return APIResult.success(1); 
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectUsers  회원조회
	@PostMapping("/incuR002")
	@ResponseBody
	public APIResult selectUsers(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_User param) throws Exception {
		try {
			LOG.info("selectUsers 유저정보 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setEmail(email);
			param.setUserCd(userCd);
			
			return APIResult.success(service.selectUsers(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectOrders  주문내역조회
	@PostMapping("/incuR003")
	@ResponseBody
	public APIResult selectOrders(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Orders param) throws Exception {
		try {
			LOG.info("selectOrders 주문정보 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectOrders(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectPurchaseOrder  운송장 등록을 위한 주문내역
	@PostMapping("/incuR004")
	@ResponseBody
	public APIResult selectPurchaseOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_PurchaseOrders param) throws Exception {
		try {
			LOG.info("selectPurchaseOrder 운송장 메뉴 구매 정보 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectPurchaseOrder(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectTrackingInfo  운송장 등록 다이얼로그
	@PostMapping("/incuR005")
	@ResponseBody
	public APIResult selectTracking(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tracking param) throws Exception {
		try {
			LOG.info("selectTracking 운송장 등록 다이얼로그 정보 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectTracking(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateTrackingNum  운송장 등록
	@PostMapping("/incuU201")
	@ResponseBody
	public APIResult updateTrackingNum (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tracking param) throws Exception {
		try {
			LOG.info("updateTrackingNum 운송장 등록하기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.updateTrackingNum(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectShipped  발송완료 메뉴
	@PostMapping("/incuR006") 
	@ResponseBody
	public APIResult selectShipped(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Shipped param) throws Exception {
		try {
			LOG.info("selectShipped 발송완료 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectShipped(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCancelling  취소요청
	@PostMapping("/incuR007") 
	@ResponseBody
	public APIResult selectCancelling(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
		try {
			LOG.info("selectCancelling 취소요청 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectCancelling(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectDetailCancelling  취소요청 메뉴에서 취소버튼클릭시
	@PostMapping("/incuR008") 
	@ResponseBody
	public APIResult selectDetailCancelling(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
		try {
			LOG.info("selectDetailCancelling 취소요청 다이얼로그 정보 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectDetailCancelling(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateCanceled  구매 취소하기
	@PostMapping("/incuU202")
	@ResponseBody
	public APIResult updateCanceled (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Cancelling param) throws Exception {
		try {
			LOG.info("updateCanceled 구매 취소하기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.updateCanceled(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectCanceled  취소내역
	@PostMapping("/incuR009")
	@ResponseBody
	public APIResult selectCanceled(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Canceled param) throws Exception {
		try {
			LOG.info("selectCanceled 취소내역 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectCanceled(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectProdOrder  재고관리 데이터 가져오기
	@PostMapping("/incuR010")
	@ResponseBody
	public APIResult selectProdOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdOrder param) throws Exception {
		try {
			LOG.info("selectProdOrder 재고관리 데이터 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectProdOrder(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateProdOrder  재고관리 수정
	@PostMapping("/incuU203")
	@ResponseBody
	public APIResult updateProdOrder (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdOrder param) throws Exception {
		try {
			LOG.info("updateProdOrder 재고관리 수정");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.updateProdOrder(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// insertProdItem  제품등록
	@PostMapping("/incuC101")
	@ResponseBody
	public APIResult insertProdItem (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_NewProd param) throws Exception {
		try {
			LOG.info("insertProdItem 제품등록");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.insertProdItem(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectSeason  시즌데이터 가져오기
	@PostMapping("/incuR011")
	@ResponseBody
	public APIResult selectSeason(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdStatus param) throws Exception {
		try {
			LOG.info("selectSeason 조회 시즌 데이터 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectSeason(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectProdStatus  제품상태 가져오기
	@PostMapping("/incuR012")
	@ResponseBody
	public APIResult selectProdStatus(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdStatus param) throws Exception {
		try {
			LOG.info("selectProdStatus 제품상태 메뉴 데이터 가져오기");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.selectProdStatus(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateProdDesc  상품 제품설명 업데이트
	@PostMapping("/incuU204")
	@ResponseBody
	public APIResult updateProdDesc (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdStatus param) throws Exception {
		try {
			LOG.info("updateProdDesc 상품 제품설명 업데이트");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.updateProdDesc(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
	// C100생성 R000출력 U200갱신 D300삭제
	// updateProd  상품 제품 업데이트
	@PostMapping("/incuU205")
	@ResponseBody
	public APIResult updateProd (HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_ProdStatus param) throws Exception {
		try {
			LOG.info("updateProd 제품 정보 업데이트");
			// 쿠키 정보 갖고오기
			String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		String roleType = (String) servletRequest.getAttribute("roleType");
			
			if(!"X".equals(roleType)) {
				return APIResult.tokenFail();
			}
			
			param.setUserCd(userCd);
			param.setEmail(email);
			
			return APIResult.success(service.updateProd(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
	
		// C100생성 R000출력 U200갱신 D300삭제
		// selectOrdersProdDetails  구매 상세 정보 가져오기
		@PostMapping("/incuR013")
		@ResponseBody
		public APIResult selectOrdersProdDetails(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			/* RequestContext session , */ @RequestBody P_Tracking param) throws Exception {
			try {
				LOG.info("selectOrdersProdDetails 구매 상세 정보 가져오기");
				// 쿠키 정보 갖고오기
				String userCd = (String) servletRequest.getAttribute("userCd");
	    		
	    		String email = (String) servletRequest.getAttribute("email");
	    		
	    		String roleType = (String) servletRequest.getAttribute("roleType");
				
				if(!"X".equals(roleType)) {
					return APIResult.tokenFail();
				}
				
				param.setUserCd(userCd);
				param.setEmail(email);
				
				return APIResult.success(service.selectOrdersProdDetails(param));
			} catch (Exception e) {
				LOG.error("Exception [Err_Msg]: {}", e.getMessage());
				return APIResult.fail(e.getMessage());
			}
		}
}
