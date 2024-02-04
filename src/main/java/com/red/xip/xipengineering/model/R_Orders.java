package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Orders {

	private String orderCd;
	private String orderDt;
	private String orderStatus;
	private String krwTotalAmount;
	private String krwShippingAmount;
	private String krwSubTotal;
	private String usdTotalAmount;
	private String usdShippingAmount;
	private String usdSubTotal;
}
