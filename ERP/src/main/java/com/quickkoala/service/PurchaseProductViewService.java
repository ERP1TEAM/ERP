package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.PurchaseProductViewDto;
import com.quickkoala.entity.PurchaseProductViewEntity;

public interface PurchaseProductViewService {
	List<PurchaseProductViewDto> getAllOrders();
}
