package com.red.xip.xipengineering.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_Tracking {

	private String shippingMethod;
	private String AddLastNm;
	private String AddFirstNm;
	private String phone;
	private String company;
	private String add1;
	private String add2;
	private String city;
	private String addCountry;
	private String state;
	private String postalCd;
	private String trackingNum;
	
	private List<TrackingProd> trackingProd;
}
