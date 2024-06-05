package com.red.xip.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_AccountDetail {

	private String lastNm;
	private String firstNm;
	private String email;
	
	private int addCount;
	private String addLastNm;
	private String addFirstNm;
	private String phone;
	private String company;
	private String add1;
	private String add2;
	private String city;
	private String addCountry;
	private String iso2;
	private String state;
	private String postalCd;
	private String userCd;
	private String creatDt;
	
	private int shipFee;
	
	private String webAuthId;
}
