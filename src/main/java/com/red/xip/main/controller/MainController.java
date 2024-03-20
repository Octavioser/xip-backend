package com.red.xip.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.red.xip.main.model.P_Imges;
import com.red.xip.main.service.MainService;
import com.red.xip.util.model.APIResult;

@RestController
@RequestMapping("/main")
public class MainController {

	@Autowired
	MainService service;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	// C100생성 R000출력 U200갱신 D300삭제
	// selectImages
	@PostMapping("/mainR001")
	@ResponseBody
	public APIResult selectImages(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
		/* RequestContext session , */ @RequestBody P_Imges param) throws Exception {
    	try {            
    		return APIResult.success(service.selectImages(param));
		} catch (Exception e) {
			LOG.error("Exception [Err_Msg]: {}", e.getMessage());
			return APIResult.fail(e.getMessage());
		}
	}
}
