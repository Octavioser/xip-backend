package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Cancelling {

	private String userCd;
	private String email;
	
	private String fromDt;
	private String toDt;
	private String userEmail;
	private String orderStatus;
	
	private String orderCd;
	private int cancelAmount;
	private String reason;
	
	private String idempotencyKey;
}
