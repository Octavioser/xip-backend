package com.red.xip.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.login.model.P_Login;
import com.red.xip.login.model.P_WebAuth;
import com.red.xip.login.model.R_Login;
import com.red.xip.login.model.R_WebAuth;

@Mapper
public interface LoginMapper {

	int selectEmailCheck(P_Login param) throws Exception;

	int insertCreateAccount(P_Login param);

	List<R_Login> test(P_Login param);
	
	List<R_Login> getLoginCheck (P_Login param);
	
	int insertAuthCd (P_Login param) throws Exception;
	
	int selectEmailAuthCodeCheck (P_Login param) throws Exception;

	List<R_WebAuth> selectWebAuthItem(P_WebAuth param) throws Exception;

	int updateSaveWebAuth(P_WebAuth param) throws Exception;

	int createWebAuthnSaveChallenge(P_WebAuth param) throws Exception;

	int updateChallenge(P_WebAuth param) throws Exception;

	List<R_WebAuth> selectEmailWebAuthCheck(P_WebAuth param) throws Exception;

	int updatePw(P_Login param) throws Exception;

	int selectEmailAuthCodeTimeCheck(P_Login param);
}
