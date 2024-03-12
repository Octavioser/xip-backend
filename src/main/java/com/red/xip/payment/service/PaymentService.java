package com.red.xip.payment.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.red.xip.common.CommonUtils;
import com.red.xip.payment.mapper.PaymentMapper;
import com.red.xip.payment.model.P_Tosspay;
import com.red.xip.payment.model.R_Tosspay;
import com.red.xip.payment.model.TossPaymentsResponse;
import org.springframework.beans.factory.annotation.Value;

@Service
public class PaymentService {

	@Autowired
	PaymentMapper mapper;
	
	@Value("${toss.pay.secret-key}")
	private String secretKey;
	
	@Value("${app.origin}")
    private String appOrigin;
	

	@Transactional(rollbackFor = Exception.class)
	public R_Tosspay insertOrder(P_Tosspay param) throws Exception {
		try {
			R_Tosspay result = new R_Tosspay();
			// 이미 구매한 제품인지 확인 
			String orderCd = mapper.orderStatusCheck(param);
			if(orderCd != null) {
				result.setOrderCd(orderCd);
				return result;
			}
			
			String price = "";
			if("cart".equals(param.getOrderMethod())) {
				price = mapper.orderCartCheck(param);
			}
			else {
				price = mapper.orderCheck(param);
			}
			
			// 가격확인
			if(!(CommonUtils.stringIfNull(price).equals(param.getAmount()))) {
				throw new RuntimeException("######### 가격이 맞지않음 원래가격==>" + price + "받아온 가격==>" + param.getAmount());
			}
			String encodedString = "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
			WebClient webClient = WebClient.builder().baseUrl("https://api.tosspayments.com").build();
			TossPaymentsResponse response = webClient.post()
	            .uri("/v1/payments/confirm")
	            .header(HttpHeaders.AUTHORIZATION, encodedString)
//	            .header(HttpHeaders.AUTHORIZATION, "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(String.format("{\"amount\":\"%s\",\"orderId\":\"%s\",\"paymentKey\":\"%s\"}", param.getAmount(), param.getOrderId(), param.getPaymentKey()))
                .retrieve()
                .bodyToMono(TossPaymentsResponse.class)
                .block(); // 동기 처리
				if("DONE".equals(response.getStatus())) {
					try {
						param.setPayMethod(response.getMethod());
						mapper.insertOrder(param);
						mapper.insertOrderD(param);
						if("cart".equals(param.getOrderMethod())) {
							mapper.deleteUserCart(param);
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException("Error: 사용자 결제 등록실패");
					}
				}
				else {
					throw new RuntimeException("Error: 사용자 결제실패");
				}
			orderCd = String.format("%08d", param.getOrderCd());
			result.setOrderCd(orderCd);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

}
