package com.red.xip.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.red.xip.shop.mapper.ShopMapper;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Shop;

@Service
public class ShopService {

	@Autowired
	ShopMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<R_Shop> selectDetailAccount (P_Shop param) throws Exception {
		
		return mapper.selectDetailAccount(param);
	}

	public int updateAccountInfoNm(P_Shop param) throws Exception {
		
		return mapper.updateAccountInfoNm(param);
	}
	
	public int updateAccountInfoPw(P_Shop param) throws Exception {
		

		List<R_Shop> resultData = mapper.getPwCheck(param);
		if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
			param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
			param.setNewPw(String.valueOf(passwordEncoder.encode(param.getNewPw())));
			return mapper.updateAccountInfoPw(param);
		}
		else {
			return -1;
		}
		
		
	}

	public int insertAdd(P_Shop param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertAdd(param);
	}
}
