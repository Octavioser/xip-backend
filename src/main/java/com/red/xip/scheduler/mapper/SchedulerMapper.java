package com.red.xip.scheduler.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulerMapper {

	int deleteEmailAuthCode();
}
