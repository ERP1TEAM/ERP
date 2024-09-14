package com.quickkoala.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.ViewReceiveSummaryEntity;

public interface ViewReceiveSummaryService {
	List<ViewReceiveSummaryEntity> getData();
	ViewReceiveSummaryEntity getOneData(String orderNumber);
	Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size);
}
