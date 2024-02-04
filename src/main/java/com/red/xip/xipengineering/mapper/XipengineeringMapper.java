package com.red.xip.xipengineering.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_User;


@Mapper
public interface XipengineeringMapper {

	List<R_User> selectUsers(P_User param) throws Exception;

	List<R_Orders> selectOrders(P_Orders param) throws Exception;
}
