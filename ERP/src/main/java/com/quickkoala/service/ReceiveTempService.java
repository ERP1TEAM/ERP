package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.dto.SupplierDeliveryListDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.ReceiveTempEntity;
import com.quickkoala.entity.ReceiveTempViewEntity;

public interface ReceiveTempService {
	List<ReceiveTempEntity> addAllReceive(SupplierDeliveryListDto orders);
	ReceiveTempEntity addDelivery(String data, Integer ea);
	long getCountOfOrdersToday();
	List<ReceiveTempEntity> getAllTemp();
	ReceiveTempEntity getOne(String data);
	ReceiveTempEntity modifyStatus(String data, Integer ea);
}
