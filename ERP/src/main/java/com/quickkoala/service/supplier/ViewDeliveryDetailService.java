package com.quickkoala.service.supplier;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;

public interface ViewDeliveryDetailService {
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size);
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size, String code, String word);
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size, SearchDto dto, String code);
	
}
