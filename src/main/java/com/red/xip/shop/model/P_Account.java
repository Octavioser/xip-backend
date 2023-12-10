package com.red.xip.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Account {
	
	private String userCd;
	private String email;
	private String firstNm;
	private String lastNm;
	private String pw;
	private String newPw;
	
	private String addFirstNm;
	private String addLastNm;
	private String phone;
	private String company;
	private String add1;
	private String add2;
	private String city;
	private String addCountry;
	private String state;
	private String postalCd;
}
