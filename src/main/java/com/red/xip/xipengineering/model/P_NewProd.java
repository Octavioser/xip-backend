package com.red.xip.xipengineering.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_NewProd {
	
	private String userCd;
	private String email;
	
	private HashMap<String,String> img;
	
	private String prodCd;
    private String name;
    private BigDecimal price;
    private BigDecimal usPrice;
    private String sizeOpt;
    private String line;
    private String season;
    private String prodDesc;
    private String prodDescD;
    private String imageSrc;
    private List<prodCdColumns> prodCdD;
    private String prodDSrc;
}
