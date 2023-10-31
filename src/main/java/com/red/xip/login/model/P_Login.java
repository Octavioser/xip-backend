package com.red.xip.login.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Login {

	private String email;
	private String pw;
	private String gender;
	private String firstNm;
	private String lastNm;
	private String authCd;
	private String country;
}
