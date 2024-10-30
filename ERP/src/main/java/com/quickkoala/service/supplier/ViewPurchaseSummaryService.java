package com.quickkoala.service.supplier;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;

public interface ViewPurchaseSummaryService {
	Page<ViewPurchaseSummaryEntity> getPaginatedData(int pno, int size);
	Page<ViewPurchaseSummaryEntity> getPaginatedData(int pno, int size, String code, String word);
	Page<ViewPurchaseSummaryEntity> getPaginatedData(int pno, int size, SearchDto dto, String code);
}
