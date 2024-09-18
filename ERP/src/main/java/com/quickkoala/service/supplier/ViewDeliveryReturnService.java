package com.quickkoala.service.supplier;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;

public interface ViewDeliveryReturnService {
	Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size);
	Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size, String code, String word);
}
