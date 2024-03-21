package com.red.xip.shop.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public List<R_AccountDetail> selectDetailAccount (P_Account param) throws Exception {
		
		return mapper.selectDetailAccount(param);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateAccountInfoNm(P_Account param) throws Exception {
		try {
			return mapper.updateAccountInfoNm(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
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
				return mapper.updateAccountInfoPw(param);
			}
			else {
				return -1;
			}
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}	
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertAdd(P_Account param) throws Exception {
		try {
			return mapper.insertAdd(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
	
	public List<R_Shop> selectProdList(P_Shop param) throws Exception {
		try {
			return mapper.selectProdList(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Shop> selectDetailProdList(P_Shop param) throws Exception {
		
		try {
			return mapper.selectDetailProdList(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	// 제품 상세페이지에서 cart에 담을때
	@Transactional(rollbackFor = Exception.class)
	public int insertCart(P_Shop param) throws Exception {
		try {
			return mapper.insertCart(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Cart> selectCart(P_Cart param) throws Exception {
		// 솔드아웃이나 판매중지된 제품 삭제
		try {
			mapper.deleteSoldoutCart();
			return mapper.selectCart(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
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
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteWebauthn(P_Account param) throws Exception {
		try {
			return mapper.deleteWebauthn(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteAccount(P_Account param) throws Exception {
		try {
			return mapper.deleteAccount(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_Order> selectOrder(P_Order param) throws Exception {		
		try {
			return mapper.selectOrder(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
		
	}

	public List<R_OrderD> selectOrderDetails(P_OrderD param) throws Exception {
		try {
			List<orderDetails> list =  mapper.selectOrderDetailProducts(param);
			List<R_OrderD> result = mapper.selectOrderDetails(param);
			if(list.size() > 0 && result.size() > 0) {
				result.get(0).setOrderDetails(list);
				return result;
			}
			else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCancelOrder(P_OrderD param) throws Exception {
		try {
			return mapper.updateCancelOrder(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateCancellingCancel(P_OrderD param) throws Exception {
		try {
			return mapper.updateCancellingCancel(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}

	public List<R_OrderD> selectCountries() throws Exception {
		try {
			return mapper.selectCountries();
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
	}
}
