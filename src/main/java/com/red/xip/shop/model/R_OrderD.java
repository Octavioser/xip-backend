package com.red.xip.shop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class R_OrderD {

	private List<orderDetails> orderDetails;
	
	private String orderDt;
	private String orderStatus;
	private String orderCd;
	
	private String shippingMethod;
	private String payMethod;
	
	private String addLastNm;
	private String addFirstNm;
	private String phone;
	private String company;
	private String add1;
	private String add2;
	private String city;
	private String addCountry;
	private String state;
	private String postalCd;
	
	private String totalAmount;
	private String shippingAmount;
	private String subtotal;
}
