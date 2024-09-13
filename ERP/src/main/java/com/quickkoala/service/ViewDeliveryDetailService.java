package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quickkoala.entity.ViewDeliveryDetailEntity;

public interface ViewDeliveryDetailService {
	List<ViewDeliveryDetailEntity> getAllData();
	Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size);
}
