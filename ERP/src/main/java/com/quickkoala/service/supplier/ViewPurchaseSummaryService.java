package com.quickkoala.service.supplier;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;

public interface ViewPurchaseSummaryService {
	Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size);
	Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size, String code, String word);
}
