package com.red.xip.xipengineering.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_ProdOrder {

	private String userCd;
	private String email;
	
	private String fromDt;
	private String toDt;
	private String season;
	
	private BigDecimal soldQty;
	private BigDecimal totalQty;
	private String prodCd;
}
