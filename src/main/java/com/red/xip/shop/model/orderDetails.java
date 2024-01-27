package com.red.xip.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class orderDetails {
	private String name;
	private String imageSrc;
	private String prodSize;
	private int prodQty;
	private String prodPrice;
	private String prodCdD;
}
