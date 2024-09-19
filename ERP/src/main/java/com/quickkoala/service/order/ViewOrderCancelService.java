package com.quickkoala.service.order;

import java.util.List;

import com.quickkoala.dto.order.OrderCancelDto;
import com.quickkoala.entity.order.ViewOrderCancelEntity;

public interface ViewOrderCancelService {
	public List<OrderCancelDto> getAll(int pg, int size);
	
}
