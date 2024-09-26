package com.quickkoala.service.receive;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;

public interface ViewReceiveSummaryService {
	ViewReceiveSummaryEntity getOneData(String orderNumber);
	Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size);
	Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size, String code, String word);
}
