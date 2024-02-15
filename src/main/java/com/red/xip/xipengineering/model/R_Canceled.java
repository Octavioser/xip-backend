package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Canceled {

	private String orderCd;
	private String userNm;
	private String email;
	private String orderDt;
	private String cancelDt;
	private String cancelAmount;
}
