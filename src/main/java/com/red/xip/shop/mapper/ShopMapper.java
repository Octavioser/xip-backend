package com.red.xip.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.P_Cart;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Account;
import com.red.xip.shop.model.R_Cart;
import com.red.xip.shop.model.R_Shop;

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
}
