package com.red.xip.xipengineering.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsS3Upload.S3Service;
import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.payment.model.TossPaymentsResponse;
import com.red.xip.xipengineering.mapper.XipengineeringMapper;
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
import com.red.xip.xipengineering.model.R_Canceled;
import com.red.xip.xipengineering.model.R_Cancelling;
import com.red.xip.xipengineering.model.R_DetailCancelling;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_PayCancel;
import com.red.xip.xipengineering.model.R_ProdOrder;
import com.red.xip.xipengineering.model.R_ProdStatus;
import com.red.xip.xipengineering.model.R_PurchaseOrders;
import com.red.xip.xipengineering.model.R_ShipDetails;
import com.red.xip.xipengineering.model.R_ShipInfo;
import com.red.xip.xipengineering.model.R_Shipped;
import com.red.xip.xipengineering.model.R_Tracking;
import com.red.xip.xipengineering.model.R_User;
import com.red.xip.xipengineering.model.TrackingProd;
import com.red.xip.xipengineering.model.XIP3010modifyData;

@Service
public class XipengineeringService {

	@Autowired
	XipengineeringMapper mapper;
	
	@Autowired
	AwsSesService awsSesService;
	
	@Autowired
	S3Service s3Service;
	
	@Value("${toss.pay.secret-key}")
	private String secretKey;
	
	@Autowired // 이메일템플릿
    private TemplateEngine templateEngine;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public List<R_User> selectUsers(P_User param) throws Exception {
		try {
			return mapper.selectUsers(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Orders> selectOrders(P_Orders param) throws Exception {
		try {
			return mapper.selectOrders(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_PurchaseOrders> selectPurchaseOrder(P_PurchaseOrders param) throws Exception{
		try {
			return mapper.selectPurchaseOrder(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Tracking> selectTracking(P_Tracking param) throws Exception{
		try {
			
			List<R_Tracking> result = mapper.selectTrackingAdd(param);
			result.get(0).setTrackingProd(mapper.selectTrackingProd(param));  
			return result;
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateTrackingNum(P_Tracking param) throws Exception{
		try {
			int result = mapper.updateTrackingNum(param); 
			
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
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Shipped> selectShipped(P_Shipped param) throws Exception{
		try {
			return mapper.selectShipped(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Cancelling> selectCancelling(P_Cancelling param) throws Exception{
		try {
			return mapper.selectCancelling(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	public List<R_DetailCancelling> selectDetailCancelling(P_Cancelling param) throws Exception{
		try {
			return mapper.selectDetailCancelling(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCanceled(P_Cancelling param) throws Exception{
		try {
			int result = mapper.updateCancelStatus(param);
			// 멱등키 
			String IdempotencyKey = UUID.randomUUID().toString();
			param.setIdempotencyKey(IdempotencyKey);
			result = mapper.insertCancel(param);
			// 토스 취소시 정보 갖고오기
			R_PayCancel payInfo=  mapper.selectCancelPayInfo(param);
			
			// 토스페이 취소 api 
			String encodedString = "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
			String url = "/v1/payments/" + payInfo.getPaymentKey() + "/cancel";
			
			WebClient webClient = WebClient.builder().baseUrl("https://api.tosspayments.com").build();
//			TossPaymentsResponse response = 
			webClient.post()
	            .uri(url)
	            .header(HttpHeaders.AUTHORIZATION, encodedString)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Idempotency-Key", payInfo.getIdempotencyKey())
                .bodyValue(String.format("{\"cancelReason\":\"%s\",\"cancelAmount\":%s,\"currency\":\"%s\"}", payInfo.getCancelReason(), payInfo.getCancelAmount(), payInfo.getCurrency()))
                .retrieve()
                .bodyToMono(TossPaymentsResponse.class)
                .block(); // 동기 처리
			// 결제가 실패할 경우 에러남
			
			return result;
			
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Canceled> selectCanceled(P_Canceled param) throws Exception{
		try {
			return mapper.selectCanceled(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_ProdOrder>  selectProdOrder(P_ProdOrder param) throws Exception{
		try {
			return mapper.selectProdOrder(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateProdOrder(P_ProdOrder param) throws Exception{
		try {
			List<XIP3010modifyData> item = param.getModifyData();
			int itemLen = item.size();
			for(int i=0; i<itemLen; i++) {
				mapper.updateProdOrder(item.get(i));
			}
			return itemLen;
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Object insertProdItem(P_NewProd param) throws Exception{
		 
		try {
			String prodCd = param.getProdCd();
			String filePath = "xItem/i/shop/products/" + prodCd + "/" + prodCd + ".gif";
			HashMap<String,String> imgItem = param.getImg();
			
			param.setImageSrc(filePath);
			
			mapper.insertProd(param);
			
			mapper.insertProdD(param.getProdCdD());

			String index; 
			
			String prodDetailImgSrc = "";
			
			for(int i=0; i<imgItem.size(); i++) {
				
				index = String.valueOf(i);
				System.out.println(imgItem.get("image" + index));
				if(i > 0) {
					filePath = "xItem/i/shop/products/" + prodCd + "/" + "detail" + "/" + prodCd + "_" + index + ".webp";
					if("".equals(prodDetailImgSrc)) {
						prodDetailImgSrc = prodDetailImgSrc + filePath;
					}
					else {
						prodDetailImgSrc = prodDetailImgSrc + "|" + filePath;
					}
				}
				
				s3Service.uploadBase64Image(imgItem.get("image" + index), filePath);
			}
			param.setProdDSrc(prodDetailImgSrc);
			return mapper.insertProdDImg(param);

		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_ProdStatus> selectSeason(P_ProdStatus param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.selectSeason(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_ProdStatus> selectProdStatus(P_ProdStatus param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.selectProdStatus(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public int updateProdDesc(P_ProdStatus param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.updateProdDesc(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public int updateProd(P_ProdStatus param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.updateProd(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<TrackingProd> selectOrdersProdDetails(P_Tracking param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.selectTrackingProd(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
}
