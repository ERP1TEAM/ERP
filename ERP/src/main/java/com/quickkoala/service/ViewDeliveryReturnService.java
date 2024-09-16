package com.quickkoala.service;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewDeliveryReturnEntity;

public interface ViewDeliveryReturnService {
	Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size);
}
