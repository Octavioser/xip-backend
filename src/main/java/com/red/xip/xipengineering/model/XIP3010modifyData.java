package com.red.xip.xipengineering.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class XIP3010modifyData {

	private BigDecimal soldQty;
	private BigDecimal totalQty;
	private String prodCdD;
}
