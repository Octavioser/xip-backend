package com.red.xip.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.red.xip.shop.model.R_Shop;
import com.red.xip.shop.mapper.ShopMapper;
import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.P_Cart;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Account;
import com.red.xip.shop.model.R_Cart;

@Service
public class ShopService {

	@Autowired
	ShopMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<R_Account> selectDetailAccount (P_Account param) throws Exception {
		
		return mapper.selectDetailAccount(param);
	}

	public int updateAccountInfoNm(P_Account param) throws Exception {
		
		return mapper.updateAccountInfoNm(param);
	}
	
	public int updateAccountInfoPw(P_Account param) throws Exception {
		

		List<R_Account> resultData = mapper.getPwCheck(param);
		if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
			param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
			param.setNewPw(String.valueOf(passwordEncoder.encode(param.getNewPw())));
			return mapper.updateAccountInfoPw(param);
		}
		else {
			return -1;
		}
		
		
	}

	public int insertAdd(P_Account param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertAdd(param);
	}
	
	public List<R_Shop> selectProdList(P_Shop param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectProdList(param);
	}

	public List<R_Shop> selectDetailProdList(P_Shop param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDetailProdList(param);
	}

	// 제품 상세페이지에서 cart에 담을때
	public int insertCart(P_Shop param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertCart(param);
	}

	public List<R_Cart> selectCart(P_Cart param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectCart(param);
	}

	public int updateCartQty(P_Cart param) throws Exception {
		// TODO Auto-generated method stub
		int cartQty = param.getProdQty();
		if(cartQty == 0) { // 제품 갯수가 0일때는 삭제
			return mapper.deleteCartQty(param);
		}
		else {
			int availprodQty = mapper.selectCartQty(param);
			if("UP".equals(param.getQtyChangeType()) && cartQty > availprodQty) { // 재고부족시
				return -3;
			}
			return mapper.updateCartQty(param);
		}
	}

}
