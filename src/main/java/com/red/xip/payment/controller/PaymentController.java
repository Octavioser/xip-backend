package com.red.xip.payment.controller;

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
import com.red.xip.payment.model.P_Tosspay;
import com.red.xip.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService service;
	
	// C100생성 R000출력 U200갱신 D300삭제
	// insertOrder
	@PostMapping("/payC101")
	@ResponseBody
	public Object insertOrder(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Tosspay param) throws Exception {
    	try {
    		// 쿠키 정보 갖고오기
    		HashMap<String, String> userInfo = CommonUtils.getUserInfoFromCookie(servletRequest);
            
    		String userCd = userInfo.get("userCd");
    		
    		String email = userInfo.get("email");
    		
    		if("".equals(CommonUtils.stringIfNull(userCd)) || "".equals(CommonUtils.stringIfNull(email))) {
    			return -2;
    		}
    		
            param.setUserCd(userCd);
            
    		return service.insertOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -2;  // -1 에러 -2 에러 및 로그아웃
		}
	}
}
