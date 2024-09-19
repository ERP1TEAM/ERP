package com.quickkoala.service.supplier;

import java.util.List;

import com.quickkoala.dto.receive.PurchaseDto;
import com.quickkoala.dto.receive.PurchaseListDto;
import com.quickkoala.entity.supplier.PurchaseEntity;

public interface PurchaseService {
	List<PurchaseEntity> addOrders(PurchaseListDto orders);
	long getCountOfOrdersToday();
	List<PurchaseEntity> getAllOrders();
	List<PurchaseEntity> getAllOrdersByStatus(String status);
	PurchaseEntity getOrderByNumber(String number);
}
