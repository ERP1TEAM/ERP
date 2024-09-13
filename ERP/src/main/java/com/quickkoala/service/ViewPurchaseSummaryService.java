package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.ViewPurchaseSummaryDto;
import com.quickkoala.entity.ViewPurchaseSummaryEntity;

public interface ViewPurchaseSummaryService {
	List<ViewPurchaseSummaryDto> getAllOrders();
}
