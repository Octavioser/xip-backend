package com.red.xip.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Order {

	private String orderDt;
	private String orderStatus;
	private String orderCd;
	private String totalAmount;
	private String cancelAble;
}
