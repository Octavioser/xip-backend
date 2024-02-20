package com.red.xip.xipengineering.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.xipengineering.mapper.XipengineeringMapper;
import com.red.xip.xipengineering.model.P_Canceled;
import com.red.xip.xipengineering.model.P_Cancelling;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_ProdOrder;
import com.red.xip.xipengineering.model.P_PurchaseOrders;
import com.red.xip.xipengineering.model.P_Shipped;
import com.red.xip.xipengineering.model.P_Tracking;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_Canceled;
import com.red.xip.xipengineering.model.R_Cancelling;
import com.red.xip.xipengineering.model.R_DetailCancelling;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_ProdOrder;
import com.red.xip.xipengineering.model.R_PurchaseOrders;
import com.red.xip.xipengineering.model.R_ShipDetails;
import com.red.xip.xipengineering.model.R_ShipInfo;
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
		try {
			return mapper.selectUsers(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_Orders> selectOrders(P_Orders param) throws Exception {
		try {
			return mapper.selectOrders(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_PurchaseOrders> selectPurchaseOrder(P_PurchaseOrders param) throws Exception{
		try {
			return mapper.selectPurchaseOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_Tracking> selectTracking(P_Tracking param) throws Exception{
		try {
			
			List<R_Tracking> result = mapper.selectTrackingAdd(param);
			result.get(0).setTrackingProd(mapper.selectTrackingProd(param));  
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateTrackingNum(P_Tracking param) throws Exception{
		try {
			int result = mapper.updateTrackingNum(param); 
			
			if (result != 1) {
	            throw new RuntimeException("######### Expected updateTrackingNum count 1, but was " + result);
	        }
			
			R_ShipInfo shipItem = mapper.selectShipInfo(param);
			
			List<R_ShipDetails> detailItem = mapper.selectShipDetails(param);
			
			String shipEmail = mapper.selectShipEmail(param);
			
			Context context = new Context();
			context.setVariable("shippInfo", shipItem);
			context.setVariable("shipDetails", detailItem);
	        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
	        String emailContent = templateEngine.process("shippedEmail", context);
	        
		    String senderEmail = "xipservice@xip.red";
		    String receiverEmail = shipEmail;
		    String emailSubject = "xip.red your order has been shipped";
			awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Shipped> selectShipped(P_Shipped param) throws Exception{
		try {
			return mapper.selectShipped(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_Cancelling> selectCancelling(P_Cancelling param) throws Exception{
		try {
			return mapper.selectCancelling(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public List<R_DetailCancelling> selectDetailCancelling(P_Cancelling param) {
		try {
			return mapper.selectDetailCancelling(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCanceled(P_Cancelling param) throws Exception{
		try {
			int result = mapper.updateCancelStatus(param);
			if (result != 1) {
	            throw new RuntimeException("######### Expected updateCancelStatus count 1, but was " + result);
	        }
			result = mapper.insertCancel(param);
			if (result != 1) {
	            throw new RuntimeException("######### Expected insertCancel count 1, but was " + result);
	        }
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Canceled> selectCanceled(P_Canceled param) throws Exception{
		try {
			return mapper.selectCanceled(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<R_ProdOrder>  selectProdOrder(P_ProdOrder param) throws Exception{
		try {
			return mapper.selectProdOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateProdOrder(P_ProdOrder param) throws Exception{
		try {
			int result = mapper.updateProdOrder(param);
			if (result != 1) {
	            throw new RuntimeException("######### Expected updateProdOrder count 1, but was " + result);
	        }
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
}
