package com.red.xip.xipengineering.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.xipengineering.mapper.XipengineeringMapper;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_PurchaseOrders;
import com.red.xip.xipengineering.model.P_Tracking;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_PurchaseOrders;
import com.red.xip.xipengineering.model.R_Shipped;
import com.red.xip.xipengineering.model.R_Tracking;
import com.red.xip.xipengineering.model.R_User;

@Service
public class XipengineeringService {

	@Autowired
	XipengineeringMapper mapper;
	
	@Autowired
	AwsSesService awsSesService;
	
	@Autowired // 이메일템플릿
    private TemplateEngine templateEngine;

	public List<R_User> selectUsers(P_User param) throws Exception {
		// TODO Auto-generated method stub
		try {
			return mapper.selectUsers(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
			// TODO: handle exception
		}
	}

	public List<R_Orders> selectOrders(P_Orders param) throws Exception {
		// TODO Auto-generated method stub
		try {
			return mapper.selectOrders(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_PurchaseOrders> selectPurchaseOrder(P_PurchaseOrders param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.selectPurchaseOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_Tracking> selectTracking(P_Tracking param) throws Exception{
		// TODO Auto-generated method stub
		try {
			
			List<R_Tracking> result = mapper.selectTrackingAdd(param);
			result.get(0).setTrackingProd(mapper.selectTrackingProd(param));  
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public int updateTrackingNum(P_Tracking param) throws Exception{
		// TODO Auto-generated method stub
		try {
			int result = mapper.updateTrackingNum(param); 
			
			List<R_Shipped> shipItem = mapper.selectShipped(param);
			
			Context context = new Context();
	        context.setVariable("trackingNum", param.getTrackingNum());
	        context.setVariable("orderCd", param.getOrderCd());
	        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
	        String emailContent = templateEngine.process("shippedEmail", context);
	        
		    String senderEmail = "xipservice@xip.red";
		    String receiverEmail = shipItem.get(0).getEmail();
		    String emailSubject = "Your Contact Left order has shipped";
			awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	
}
