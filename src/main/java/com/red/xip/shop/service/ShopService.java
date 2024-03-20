package com.red.xip.shop.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.red.xip.shop.model.R_Shop;
import com.red.xip.shop.model.orderDetails;
import com.red.xip.shop.mapper.ShopMapper;
import com.red.xip.shop.model.CartList;
import com.red.xip.shop.model.P_Account;
import com.red.xip.shop.model.P_Cart;
import com.red.xip.shop.model.P_Order;
import com.red.xip.shop.model.P_OrderD;
import com.red.xip.shop.model.P_Shop;
import com.red.xip.shop.model.R_Account;
import com.red.xip.shop.model.R_AccountDetail;
import com.red.xip.shop.model.R_Cart;
import com.red.xip.shop.model.R_Order;
import com.red.xip.shop.model.R_OrderD;

@Service
public class ShopService {

	@Autowired
	ShopMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public List<R_AccountDetail> selectDetailAccount (P_Account param) throws Exception {
		
		return mapper.selectDetailAccount(param);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateAccountInfoNm(P_Account param) throws Exception {
		try {
			int result = mapper.updateAccountInfoNm(param);
			if (result != 1) {
				throw new RuntimeException("######### Expected updateAccountInfoNm count 1, but was " + result);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateAccountInfoPw(P_Account param) throws Exception {
		try {
			List<R_Account> resultData = mapper.getPwCheck(param);
			if(resultData.size() > 0 && passwordEncoder.matches(param.getPw(), resultData.get(0).getPw()) ) {
				param.setPw(String.valueOf(passwordEncoder.encode(param.getPw())));
				param.setNewPw(String.valueOf(passwordEncoder.encode(param.getNewPw())));
				int result = mapper.updateAccountInfoPw(param);
				if (result != 1) {
					throw new RuntimeException("######### Expected updateAccountInfoPw count 1, but was " + result);
				}
				return result;
			}
			else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}	
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertAdd(P_Account param) throws Exception {
		try {
			return mapper.insertAdd(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	public List<R_Shop> selectProdList(P_Shop param) throws Exception {
		return mapper.selectProdList(param);
	}

	public List<R_Shop> selectDetailProdList(P_Shop param) throws Exception {
		return mapper.selectDetailProdList(param);
	}

	// 제품 상세페이지에서 cart에 담을때
	@Transactional(rollbackFor = Exception.class)
	public int insertCart(P_Shop param) throws Exception {
		try {
			return mapper.insertCart(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Cart> selectCart(P_Cart param) throws Exception {
		// 솔드아웃이나 판매중지된 제품 삭제
		mapper.deleteSoldoutCart();
		
		return mapper.selectCart(param);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCartQty(P_Cart param) throws Exception {
		try {
			List<CartList> item = param.getCartList();
			CartList detailItem;
			
			for(int i=0; i<item.size(); i++) {
				detailItem = item.get(i); 
				detailItem.setUserCd(param.getUserCd());
				if("X".equals(detailItem.getProdQty())) {
					mapper.deleteCartQty(detailItem);
				}
				else {
					mapper.updateCartQty(detailItem);
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteWebauthn(P_Account param) throws Exception {
		try {
			int result = mapper.deleteWebauthn(param);
			if (result != 1) {
				throw new RuntimeException("######### Expected deleteWebauthn count 1, but was " + result);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteAccount(P_Account param) throws Exception {
		try {
			int result = mapper.deleteAccount(param);
			if (result != 1) {
				throw new RuntimeException("######### Expected deleteAccount count 1, but was " + result);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Order> selectOrder(P_Order param) throws Exception {
		// 솔드아웃이나 판매중지된 제품 삭제
		mapper.deleteSoldoutCart();
		return mapper.selectOrder(param);
	}

	public List<R_OrderD> selectOrderDetails(P_OrderD param) throws Exception {
		List<orderDetails> list =  mapper.selectOrderDetailProducts(param);
		List<R_OrderD> result = mapper.selectOrderDetails(param);
		if(list.size() > 0 && result.size() > 0) {
			result.get(0).setOrderDetails(list);
			return result;
		}
		else {
			return Collections.emptyList();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCancelOrder(P_OrderD param) throws Exception {
		try {
			int result = mapper.updateCancelOrder(param);
			if (result != 1) {
			    throw new RuntimeException("######### Expected updateCancleOrder count 1, but was " + result);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCancellingCancel(P_OrderD param) throws Exception {
		try {
			int result = mapper.updateCancellingCancel(param);
			if (result != 1) {
			    throw new RuntimeException("######### Expected updateCancellingCancel count 1, but was " + result);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
