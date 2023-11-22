package com.red.xip.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Shop;

@Mapper
public interface ShopMapper {

	List<R_Shop> selectDetailAccount(P_Shop param) throws Exception;
	
	int updateAccountInfoNm(P_Shop param) throws Exception;
	
	List<R_Shop> getPwCheck(P_Shop param) throws Exception;
	
	int updateAccountInfoPw(P_Shop param) throws Exception;
}
