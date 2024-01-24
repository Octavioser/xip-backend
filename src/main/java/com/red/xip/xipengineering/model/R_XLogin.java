package com.red.xip.xipengineering.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_XLogin {
	
	private String webAuthId;
	private String challenge;
	private String aaguid;
	private String pk;
	private String pkAlgo;
	
	private String userCd;
	private String email;
	private String token;
	
	private String roleType;
}
