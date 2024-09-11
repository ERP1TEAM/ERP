package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.ReceiveSummaryEntity;

public interface ReceiveSummaryViewService {
	List<ReceiveSummaryEntity> getData();
	ReceiveSummaryEntity getOneData(String orderNumber);
}
