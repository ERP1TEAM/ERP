package com.quickkoala.service.order;

import java.util.List;

import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;

import jakarta.transaction.Transactional;

public interface OrderService {
	
	@Transactional
	String updateStatus(String id,String status,String manager);
	
	
	
}
