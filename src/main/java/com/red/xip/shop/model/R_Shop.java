package com.red.xip.shop.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Shop {
	private String prodCd;
	private String name;
	private BigDecimal price;
	private String Status;
	private String ImageSrc;
	private String prodDesc;
	private String prodDescD;
	private String prodCdD;
	private String prodSize;
	private String prodStatus;
	private BigDecimal usPrice;
	private String imgSrc;
}
