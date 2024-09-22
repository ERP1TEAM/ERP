package com.quickkoala.service.order;


import org.springframework.data.domain.Page;

import com.quickkoala.entity.order.ViewOrderOngoingEntity;
public interface ViewOrderOngoingService {
	
	Page<ViewOrderOngoingEntity> getAll(int pg, int size,String select, String param);
	
}
