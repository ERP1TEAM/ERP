package com.quickkoala.service.receive;

import java.util.List;

import com.quickkoala.dto.supplier.SupplierDeliveryListDto;
import com.quickkoala.entity.receive.ReceiveTempEntity;

public interface ReceiveTempService {
	ReceiveTempEntity addDelivery(String data, Integer ea, String code);
	List<ReceiveTempEntity> getAllTemp();
	ReceiveTempEntity getOne(String data);
	ReceiveTempEntity modifyStatus(String data, Integer ea);
	Integer getWtQuantity(String order);
	String getOrderNumber(String data);
	void removeData(String code);
	String getMaxCode();
	
}
