package com.red.xip.xipengineering.model;
import java.util.List;

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
	private String searchProdCd;
	private String sizeStatus;
	
	private List<XIP3010modifyData> modifyData;
	
	
}
