package com.red.xip.payment.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.red.xip.payment.model.P_Tosspay;

@Mapper
public interface PaymentMapper {

	String orderCartCheck(P_Tosspay param) throws Exception;
	
	String orderCheck(P_Tosspay param) throws Exception;

	int insertOrder(P_Tosspay param) throws Exception;

	int insertOrderD(P_Tosspay param) throws Exception;

	int deleteUserCart(P_Tosspay param) throws Exception;

	String orderStatusCheck(P_Tosspay param) throws Exception;
}
