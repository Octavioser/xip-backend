package com.red.xip.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.xip.shop.mapper.ShopMapper;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Shop;

@Service
public class ShopService {

	@Autowired
	ShopMapper mapper;
	
	public List<R_Shop> selectDetailAccount (P_Shop param) throws Exception {
		return mapper.selectDetailAccount(param);
	}
}
