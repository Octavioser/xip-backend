package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_PurchaseOrders {

	private String userCd;
	private String email;
	
	private String toDt;
	private String fromDt;
	private String orderStatus;
}
