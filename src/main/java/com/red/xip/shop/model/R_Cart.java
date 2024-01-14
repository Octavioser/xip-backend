package com.red.xip.shop.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Cart {

	private String name;
	private BigDecimal price;
	private String imageSrc;
	private String prodSize;
	private String prodCdD;
	private int prodQty;
	private BigDecimal usPrice;
}
