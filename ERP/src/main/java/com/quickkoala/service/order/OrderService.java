package com.quickkoala.service.order;

import java.util.List;

import com.quickkoala.entity.order.OrderEntity;

import jakarta.transaction.Transactional;

public interface OrderService {
	
	@Transactional
	String updateStatus(String id,String status);
	public String updateApproved(String id);
	
	
	
}
