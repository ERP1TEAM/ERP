package com.quickkoala.service.order;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.order.ViewOrderCancelEntity;

public interface ViewOrderCancelService {
	public Page<ViewOrderCancelEntity> getAll(int pg, int size,String select, String param, String startDate, String endDate);
	
}
