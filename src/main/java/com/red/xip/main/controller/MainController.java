package com.red.xip.main.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.red.xip.common.CommonUtils;
import com.red.xip.main.mapper.MainMapper;
import com.red.xip.main.model.P_Imges;
import com.red.xip.main.service.MainService;

@RestController
@RequestMapping("/main")
public class MainController {

	@Autowired
	MainService service;
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectImages
	@PostMapping("/mainR001")
	@ResponseBody
	public Object selectImages(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Imges param) throws Exception {
    	try {            
    		return service.selectImages(param);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;  // -1 에러 -2 에러 및 로그아웃
		}
	}
}
