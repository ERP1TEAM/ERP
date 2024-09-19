package com.quickkoala.service.order;

import java.util.List;

import com.quickkoala.dto.order.OrderOngoingDto;
public interface ViewOrderOngoingService {
	
	List<OrderOngoingDto> getAll(int pg, int size);
	
}
