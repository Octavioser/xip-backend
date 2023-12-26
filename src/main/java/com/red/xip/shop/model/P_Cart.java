package com.red.xip.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Cart {

	private String userCd;
	private String prodCdD;
	private String qtyChangeType;
	private int prodQty;
}
