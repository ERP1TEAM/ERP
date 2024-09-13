package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewPurchaseDetailEntity;

public interface ViewPurchaseDetailService {
	List<ViewPurchaseDetailEntity> getData();
	List<ViewPurchaseDetailEntity> getDataByStatus(String status);
	Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size);
	Page<ViewPurchaseDetailEntity> getPaginatedDataByStatus(String status, int pno, int size);
}
