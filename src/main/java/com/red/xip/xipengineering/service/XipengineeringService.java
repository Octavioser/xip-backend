package com.red.xip.xipengineering.service;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.xip.xipengineering.mapper.XipengineeringMapper;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_User;

@Service
public class XipengineeringService {

	@Autowired
	XipengineeringMapper mapper;

	public List<R_User> selectUsers(P_User param) throws Exception {
		// TODO Auto-generated method stub
		try {
			return mapper.selectUsers(param);
		} catch (Exception e) {
			return Collections.emptyList();
			// TODO: handle exception
		}
	}

	
}
