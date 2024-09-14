package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewReceiveTempEntity;

public interface ViewReceiveTempService {
	List<ViewReceiveTempEntity> getAllOrders(String status);
	Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size);
}
