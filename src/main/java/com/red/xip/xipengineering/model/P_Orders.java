package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Orders {
	private String userCd;
	private String email;
	
	private String orderStatus;
	private String toDt;
	private String fromDt;
}
