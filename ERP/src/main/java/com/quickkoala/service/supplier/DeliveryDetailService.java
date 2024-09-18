package com.quickkoala.service.supplier;

import java.util.List;

import com.quickkoala.entity.supplier.DeliveryDetailEntity;

public interface DeliveryDetailService {
	long getCountOfOrdersToday();
	DeliveryDetailEntity addDelivery(String data, Integer ea);
	List<DeliveryDetailEntity> getDelivery();
}
