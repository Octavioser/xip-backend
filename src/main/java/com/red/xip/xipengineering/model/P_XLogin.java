package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_XLogin {

	private String challenge;
	private String userCd;
	private String email;
	
	private String authenticatorData;
	private String clientDataJSON;
	private String signature;
	private String userHandle;
	
}
