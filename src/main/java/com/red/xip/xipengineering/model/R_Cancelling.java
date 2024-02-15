package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Cancelling {

	private String orderCd;
	private String userNm;
	private String email;
	private String orderDt;
	private String addNm;
	private String totalAmount;
	private String shippingAmount;
	private String subTotal;
	private String cancelBtn;
	private String orderStatus;
}
