package com.red.xip.main.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.xip.main.mapper.MainMapper;
import com.red.xip.main.model.P_Imges;
import com.red.xip.main.model.R_Imges;

@Service
public class MainService {

	@Autowired
	MainMapper mapper;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public List<R_Imges> selectImages(P_Imges param) throws Exception{
		// TODO Auto-generated method stub
		try {
			return mapper.selectImages(param);
		} catch (Exception e) {
			LOG.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
			throw e; // 예외를 다시 던져서 Spring의 트랜잭션 롤백을 트리거
		}
		
	}
}
