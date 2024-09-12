package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.SupplierDeliveryListDto;
import com.quickkoala.entity.ReceiveTempEntity;

public interface ReceiveTempService {
	List<ReceiveTempEntity> addAllReceive(SupplierDeliveryListDto orders);
	ReceiveTempEntity addDelivery(String data, Integer ea);
	long getCountOfOrdersToday();
	List<ReceiveTempEntity> getAllTemp();
	ReceiveTempEntity getOne(String data);
	ReceiveTempEntity modifyStatus(String data, Integer ea);
	Integer getWtQuantity(String order);
	String getOrderNumber(String data);
	void removeData(String code);
}
