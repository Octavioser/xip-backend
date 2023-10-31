package com.red.xip.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.red.xip.scheduler.mapper.SchedulerMapper;


@Service
public class SchedulerService {
	
	@Autowired
	SchedulerMapper mapper;
					 //초 분 시  
	@Scheduled(cron = "0 41 21 * * ?")
	public void deleteAuthCode() {
		mapper.deleteEmailAuthCode();
	}
}
