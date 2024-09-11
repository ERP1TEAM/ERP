package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.PurchaseSummaryViewDto;
import com.quickkoala.entity.PurchaseSummaryViewEntity;

public interface PurchaseSummaryViewService {
	List<PurchaseSummaryViewDto> getAllOrders();
}
