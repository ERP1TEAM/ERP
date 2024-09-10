package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.entity.PurchaseEntity;

public interface PurchaseService {
	List<PurchaseEntity> addOrders(PurchaseListDto orders);
	long getCountOfOrdersToday();
	List<PurchaseEntity> getAllOrders();
	List<PurchaseEntity> getAllOrdersByStatus(String status);
	PurchaseEntity getOrderByNumber(String number);
}
