package com.red.xip.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.R_Account;

@Mapper
public interface ShopMapper {

	List<R_Account> selectDetailAccount(P_Account param) throws Exception;
	
	int updateAccountInfoNm(P_Account param) throws Exception;
	
	List<R_Account> getPwCheck(P_Account param) throws Exception;
	
	int updateAccountInfoPw(P_Account param) throws Exception;
	
	int insertAdd(P_Account param) throws Exception;
}
