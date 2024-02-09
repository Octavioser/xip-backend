package com.red.xip.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.P_Cart;
import com.red.xip.shop.model.P_Order;
import com.red.xip.shop.model.P_OrderD;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Account;
import com.red.xip.shop.model.R_Cart;
import com.red.xip.shop.model.R_Order;
import com.red.xip.shop.model.R_OrderD;
import com.red.xip.shop.model.R_Shop;
import com.red.xip.shop.model.orderDetails;

@Mapper
public interface ShopMapper {

	List<R_Account> selectDetailAccount(P_Account param) throws Exception;
	
	int updateAccountInfoNm(P_Account param) throws Exception;
	
	List<R_Account> getPwCheck(P_Account param) throws Exception;
	
	int updateAccountInfoPw(P_Account param) throws Exception;
	
	int insertAdd(P_Account param) throws Exception;

	List<R_Shop> selectProdList(P_Shop param) throws Exception;

	List<R_Shop> selectDetailProdList(P_Shop param) throws Exception;

	int insertCart(P_Shop param) throws Exception;

	List<R_Cart> selectCart(P_Cart param) throws Exception;

	int updateCartQty(P_Cart param) throws Exception;

	int deleteCartQty(P_Cart param) throws Exception;

	int selectCartQty(P_Cart param) throws Exception;

	int deleteWebauthn(P_Account param) throws Exception;

	int deleteAccount(P_Account param) throws Exception;

	List<R_Order> selectOrder(P_Order param) throws Exception;

	List<R_OrderD> selectOrderDetails(P_OrderD param) throws Exception;

	List<orderDetails> selectOrderDetailProducts(P_OrderD param) throws Exception;

	int updateCancleOrder(P_OrderD param) throws Exception;
}
