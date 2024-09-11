package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.ReceiveTempViewEntity;

public interface ReceiveTempViewService {
	List<ReceiveTempViewEntity> getAllOrders(String status);
}
