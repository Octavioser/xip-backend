package com.red.xip.payment.controller;

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
import com.red.xip.payment.model.P_Tosspay;
import com.red.xip.payment.service.PaymentService;
import com.red.xip.util.model.APIResult;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService service;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	// C100생성 R000출력 U200갱신 D300삭제
	// insertOrder
	@PostMapping("/payC101")
	@ResponseBody
	public APIResult insertOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tosspay param) throws Exception {
    	try {
    		LOG.info("insertOrder 결제하기");
    		// 쿠키 정보 갖고오기
    		String userCd = (String) servletRequest.getAttribute("userCd");
    		
    		String email = (String) servletRequest.getAttribute("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return APIResult.tokenFail();
    		}
    		
            param.setUserCd(userCd);
            param.setEmail(email);
            
    		return APIResult.success(service.insertOrder(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
}
