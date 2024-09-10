package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.DeliveryDetailEntity;

public interface DeliveryDetailService {
	long getCountOfOrdersToday();
	DeliveryDetailEntity addDelivery(String data, Integer ea);
	List<DeliveryDetailEntity> getDelivery();
}
