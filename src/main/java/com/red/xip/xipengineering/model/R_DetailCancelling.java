package com.red.xip.xipengineering.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_DetailCancelling {

	private String prodCdD;
	private String name;
	private String prodSize;
	private String prodQty;
	private BigDecimal price;
	private String currency;
}
