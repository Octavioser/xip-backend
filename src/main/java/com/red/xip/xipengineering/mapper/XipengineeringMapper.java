package com.red.xip.xipengineering.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.red.xip.xipengineering.model.P_Canceled;
import com.red.xip.xipengineering.model.P_Cancelling;
import com.red.xip.xipengineering.model.P_NewProd;
import com.red.xip.xipengineering.model.P_Orders;
import com.red.xip.xipengineering.model.P_ProdOrder;
import com.red.xip.xipengineering.model.P_ProdStatus;
import com.red.xip.xipengineering.model.P_PurchaseOrders;
import com.red.xip.xipengineering.model.P_Shipped;
import com.red.xip.xipengineering.model.P_Tracking;
import com.red.xip.xipengineering.model.P_User;
import com.red.xip.xipengineering.model.R_Canceled;
import com.red.xip.xipengineering.model.R_Cancelling;
import com.red.xip.xipengineering.model.R_DetailCancelling;
import com.red.xip.xipengineering.model.R_Orders;
import com.red.xip.xipengineering.model.R_PayCancel;
import com.red.xip.xipengineering.model.R_ProdOrder;
import com.red.xip.xipengineering.model.R_ProdStatus;
import com.red.xip.xipengineering.model.R_PurchaseOrders;
import com.red.xip.xipengineering.model.R_ShipDetails;
import com.red.xip.xipengineering.model.R_ShipInfo;
import com.red.xip.xipengineering.model.R_Shipped;
import com.red.xip.xipengineering.model.R_Tracking;
import com.red.xip.xipengineering.model.R_User;
import com.red.xip.xipengineering.model.TrackingProd;
import com.red.xip.xipengineering.model.prodCdColumns;


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

	List<R_Shipped> selectShipped(P_Shipped param) throws Exception;

	List<R_Cancelling> selectCancelling(P_Cancelling param) throws Exception;

	int updateCancelStatus(P_Cancelling param) throws Exception;

	int insertCancel(P_Cancelling param) throws Exception;

	List<R_Canceled> selectCanceled(P_Canceled param) throws Exception;

	List<R_DetailCancelling> selectDetailCancelling(P_Cancelling param) throws Exception;

	List<R_ProdOrder> selectProdOrder(P_ProdOrder param) throws Exception;

	int updateProdOrder(P_ProdOrder param) throws Exception;

	int insertProdD(List<prodCdColumns> prodCdD) throws Exception;
	
	int insertProd(P_NewProd param) throws Exception;

	int insertProdDImg(P_NewProd param) throws Exception;

	List<R_ProdStatus> selectSeason(P_ProdStatus param) throws Exception;

	List<R_ProdStatus> selectProdStatus(P_ProdStatus param) throws Exception;

	int updateProdDesc(P_ProdStatus param) throws Exception;

	int updateProd(P_ProdStatus param) throws Exception;

	R_PayCancel selectCancelPayInfo(P_Cancelling param) throws Exception;
}
