package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewReceiveSummaryEntity;
import com.quickkoala.repository.ViewReceiveSummaryRepository;

@Service
public class ViewReceiveSummaryServiceImpl implements ViewReceiveSummaryService{
	@Autowired
	private ViewReceiveSummaryRepository viewReceiveSummaryRepository;
	
	@Override
	public List<ViewReceiveSummaryEntity> getData() {
		return viewReceiveSummaryRepository.findAllByOrderByOrderNumberDesc();
	}
	
	@Override
	public ViewReceiveSummaryEntity getOneData(String orderNumber) {
		return viewReceiveSummaryRepository.findByOrderNumber(orderNumber);
	}
	
	@Override
	public Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewReceiveSummaryRepository.findAllByOrderByOrderNumberDesc(pageable);
	}
}
