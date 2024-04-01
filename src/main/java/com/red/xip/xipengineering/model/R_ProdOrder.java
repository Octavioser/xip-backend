package com.red.xip.xipengineering.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_ProdOrder {

	private String prodCdD;
	private String name;
	private String prodSize;
	private BigDecimal soldQty;
	private BigDecimal totalQty;
	private BigDecimal stockQty;
	private BigDecimal prodQty;
	private BigDecimal cancelQty;
	private BigDecimal krwSubTotal;
	private BigDecimal usdSubTotal;
	private BigDecimal soldTotal;
	private String sizeStatus;
}
