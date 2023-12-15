package com.red.xip.shop.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Shop {
	String prodCd;
	String name;
	BigDecimal price;
	String Status;
	String ImageSrc;
	String prodDesc;
	String prodDescD;
	String prodCdD;
	String prodSize;
	String prodStatus;
}
