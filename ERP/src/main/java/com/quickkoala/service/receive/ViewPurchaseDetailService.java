package com.quickkoala.service.receive;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;

public interface ViewPurchaseDetailService {
	Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size);
	Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size, String code, String word);
	Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size, SearchDto dto);
	Page<ViewPurchaseDetailEntity> getPaginatedDataByStatus(String status, int pno, int size);
}
