package com.red.xip.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.xip.main.mapper.MainMapper;
import com.red.xip.main.model.P_Imges;
import com.red.xip.main.model.R_Imges;

@Service
public class MainService {

	@Autowired
	MainMapper mapper;

	public List<R_Imges> selectImages(P_Imges param) throws Exception{
		// TODO Auto-generated method stub
		return mapper.selectImages(param);
	}
}
