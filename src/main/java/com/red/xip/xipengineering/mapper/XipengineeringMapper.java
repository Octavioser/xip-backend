package com.red.xip.xipengineering.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_PurchaseOrders;
import com.red.xip.xipengineering.model.P_Tracking;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_PurchaseOrders;
import com.red.xip.xipengineering.model.R_ShipDetails;
import com.red.xip.xipengineering.model.R_ShipInfo;
import com.red.xip.xipengineering.model.R_Tracking;
import com.red.xip.xipengineering.model.R_User;
import com.red.xip.xipengineering.model.TrackingProd;


@Mapper
public interface XipengineeringMapper {

	List<R_User> selectUsers(P_User param) throws Exception;

	List<R_Orders> selectOrders(P_Orders param) throws Exception;

	List<R_PurchaseOrders> selectPurchaseOrder(P_PurchaseOrders param) throws Exception;

	List<R_Tracking> selectTrackingAdd(P_Tracking param) throws Exception;

	List<TrackingProd> selectTrackingProd(P_Tracking param) throws Exception;

	int updateTrackingNum(P_Tracking param) throws Exception;

	R_ShipInfo selectShipInfo(P_Tracking param) throws Exception;

	String selectShipEmail(P_Tracking param) throws Exception;

	List<R_ShipDetails> selectShipDetails(P_Tracking param) throws Exception;
}
