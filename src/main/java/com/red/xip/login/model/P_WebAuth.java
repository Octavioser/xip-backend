package com.red.xip.login.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_WebAuth {

	private String email;
	private String pw;
	private String webAuthId;
	private String clientDataJSON;
	private String authenticatorData;
	private String signature;
	private String attestationObject;
	private String pk;
	private String userCd;
	
	private String challenge;
	private String userHandle;
	private String pkAlgo;
	private String aaguid;
	

}
