package com.red.xip.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.main.model.P_Imges;
import com.red.xip.main.model.R_Imges;

@Mapper
public interface MainMapper {

	List<R_Imges> selectImages(P_Imges param) throws Exception;

}
