package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_ShipInfo {

	private String orderCd;
	private String shippingMethod;
	private String trackingNum;
	private String addNm;
	private String add1;
	private String add2;
	private String cityPostal;
	private String totalAmount;
	private String shippingAmount;
	private String subTotal;
}
