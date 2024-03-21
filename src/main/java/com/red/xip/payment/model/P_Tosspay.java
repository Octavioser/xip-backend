package com.red.xip.payment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Tosspay {

	private String userCd;	
	private String amount;
	private String orderId;
	private String paymentKey;
	private String orderMethod;
	private String payMethod;
	private int orderCd;
	private String pgName;
	private String email;
	private String currency;
}
