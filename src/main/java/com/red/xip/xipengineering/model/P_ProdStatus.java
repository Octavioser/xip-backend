package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_ProdStatus {

	private String userCd;
	private String email;
	
	private String season;
	private String line;
	private String name;
	private String prodCd;
	private String prodDesc;
	private String prodDescD;
	
	private int price;
	private int usPrice;
	private String sizeOpt;
	private String status;
}
