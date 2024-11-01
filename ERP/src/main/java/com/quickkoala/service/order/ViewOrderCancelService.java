package com.quickkoala.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.order.OrderCancelDto;
import com.quickkoala.entity.order.ViewOrderCancelEntity;

public interface ViewOrderCancelService {
	public Page<ViewOrderCancelEntity> getAll(int pg, int size,String select, String param, String startDate, String endDate);
	
}
