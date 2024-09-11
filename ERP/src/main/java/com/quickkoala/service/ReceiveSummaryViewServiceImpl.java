package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ReceiveSummaryEntity;
import com.quickkoala.repository.ReceiveSummaryViewRepository;

@Service
public class ReceiveSummaryViewServiceImpl implements ReceiveSummaryViewService{
	@Autowired
	private ReceiveSummaryViewRepository receiveSummaryViewRepository;
	
	@Override
	public List<ReceiveSummaryEntity> getData() {
		return receiveSummaryViewRepository.findAllByOrderByOrderNumberDesc();
	}
	
	@Override
	public ReceiveSummaryEntity getOneData(String orderNumber) {
		return receiveSummaryViewRepository.findByOrderNumber(orderNumber);
	}
}
