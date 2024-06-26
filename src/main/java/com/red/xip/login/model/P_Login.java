package com.red.xip.login.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Login {

	private String email;
	private String pw;
	private String firstNm;
	private String lastNm;
	private String authCd;
	private String country;
	
	private BigDecimal termsofuse;
	private BigDecimal privacy;
}
