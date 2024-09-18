package com.quickkoala.service.supplier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;

public interface ViewDeliveryDetailService {
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size);
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size, String code, String word);
	
}
