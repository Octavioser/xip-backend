package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_PayCancel {

	private String cancelReason;
	private String currency;
	private String paymentKey;
	private String cancelAmount;
	private String idempotencyKey;
}
