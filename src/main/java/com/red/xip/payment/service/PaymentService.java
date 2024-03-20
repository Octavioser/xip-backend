package com.red.xip.payment.service;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.red.xip.awsSesEmail.service.AwsSesService;
import com.red.xip.common.CommonUtils;
import com.red.xip.payment.mapper.PaymentMapper;
import com.red.xip.payment.model.P_Tosspay;
import com.red.xip.payment.model.R_PurchaseEmail;
import com.red.xip.payment.model.R_Tosspay;
import com.red.xip.payment.model.TossPaymentsResponse;

import org.springframework.beans.factory.annotation.Value;

@Service
public class PaymentService {

	@Autowired
	PaymentMapper mapper;
	
	@Value("${toss.pay.secret-key}")
	private String secretKey;
	
	@Autowired
	AwsSesService awsSesService;
	
	@Autowired // 이메일템플릿
    private TemplateEngine templateEngine;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	

	@Transactional(rollbackFor = Exception.class)
	public R_Tosspay insertOrder(P_Tosspay param) throws Exception {
		try {
			R_Tosspay result = new R_Tosspay();
			// 이미 구매한 제품인지 확인 
			String orderCd = mapper.orderStatusCheck(param);
			if (orderCd == null) {
			    // orderCd가 null일 경우의 처리 로직
				// 실제 결제되야하는 가격 값 가져오기
				String price = "";
				if("cart".equals(param.getOrderMethod())) {
					price = mapper.orderCartCheck(param);
				}
				else {
					price = mapper.orderCheck(param);
				}
				
				// 결제된 가격과 데이터상 가격 맞는지 비교
				if(!(CommonUtils.stringIfNull(price).equals(param.getAmount()))) {
					throw new RuntimeException("######### 가격이 맞지않음 원래가격==>" + price + "받아온 가격==>" + param.getAmount());
				}
				
				// 오더 저장하기
				mapper.insertOrder(param);
				mapper.insertOrderD(param);
				if("cart".equals(param.getOrderMethod())) {
					// 장바구니 제품 구매시 장바구니 삭제
					mapper.deleteUserCart(param);
				}
				
				// 구매 수량 업데이트
				int updateRows = mapper.updateProdQty(param);
				// 재고 없을시
				if(updateRows < 1) {
					throw new RuntimeException("Error: 재고없음");
				}
				
				// 토스페이 결제 확인 api 
				String encodedString = "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
				
				WebClient webClient = WebClient.builder().baseUrl("https://api.tosspayments.com").build();
				TossPaymentsResponse response = webClient.post()
		            .uri("/v1/payments/confirm")
		            .header(HttpHeaders.AUTHORIZATION, encodedString)
	                .header(HttpHeaders.CONTENT_TYPE, "application/json")
	                .bodyValue(String.format("{\"amount\":\"%s\",\"orderId\":\"%s\",\"paymentKey\":\"%s\"}", param.getAmount(), param.getOrderId(), param.getPaymentKey()))
	                .retrieve()
	                .bodyToMono(TossPaymentsResponse.class)
	                .block(); // 동기 처리
				if(!"DONE".equals(response.getStatus())) {
					throw new RuntimeException("Error: 사용자 결제실패");
				}
				
				param.setPayMethod(response.getMethod());
				
				// 주문번호 형식 맞추기
				orderCd = String.format("%08d", param.getOrderCd());
				
				
				// 토스결제 확인 요청 후 
				confirmAfter(param, price, orderCd);
				
			}
			result.setOrderCd(orderCd);
			return result;
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	
	// 결제승인이후 동작 (결제 확인후 롤백되지 않도록 별도로 실행)
    public void confirmAfter(P_Tosspay param, String price, String orderCd) {
        try {
        	mapper.updatePayMethod(param);
        	// 이메일에 넣을 영수증 값
			List<R_PurchaseEmail> detailItem = mapper.selectOrderDetails(param);
			
			DecimalFormat df = new DecimalFormat("###,###");
			String totalAmount = "₩" + df.format( Double.parseDouble(price));

			// 이메일 보내기
			Context context = new Context();
			context.setVariable("orderCd", "XIP-" + orderCd);
			context.setVariable("detailItem", detailItem);
			context.setVariable("subTotal", totalAmount);
			context.setVariable("totalAmount", totalAmount);
			context.setVariable("shippingAmount", "₩0");
	        // 템플릿 엔진을 사용하여 HTML 컨텐츠 생성
	        String emailContent = templateEngine.process("purchaseEmail", context);
	        
		    String senderEmail = "xipservice@xip.red";
		    String receiverEmail = param.getEmail();
		    String emailSubject = "xip.red thank you for your purchase!";
		    
            awsSesService.sendEmail(emailContent, senderEmail, receiverEmail, emailSubject);
            
        } catch (Exception e) {
            // 로그 기록 또는 에러 처리
        	LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
        	// throw e를 주석하여 발생한 예외는 롤백에 영향을 주지 않음
        }
    }

}
