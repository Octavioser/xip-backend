package com.red.xip.shop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class P_Cart {

	private String userCd;
	private List<CartList> cartList;
}
