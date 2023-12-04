package com.red.xip.login.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Login {

	private String email;
	private String pw;
	private String lastNm;
	private String token;
	private String authCd;
	private String userCd;
}
