package com.quickkoala.service.order;

import jakarta.transaction.Transactional;

public interface OrderService {
	
	@Transactional
	String updateStatus(String id,String status,String manager);
	
	
	
}
