package com.red.xip.login.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_WebAuth {

	private String email;
	private String webAuthId;
	private String challenge;
	private String pk;
	private String userCd;
	private String userIdBase64;
	private String pw;
	private String aaguid;
	private String pkAlgo;
	private String token;
}
