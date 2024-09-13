package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewPurchaseDetailEntity;
import com.quickkoala.repository.ViewPurchaseDetailRepository;

@Service
public class ViewPurchaseDetailServiceImpl implements ViewPurchaseDetailService{

	@Autowired
	private ViewPurchaseDetailRepository viewPurchaseDetailRepository;
	
	@Override
	public List<ViewPurchaseDetailEntity> getData() {
		return viewPurchaseDetailRepository.findAllByOrderByOrderNumberDesc();
	}
	
	@Override
	public List<ViewPurchaseDetailEntity> getDataByStatus(String status) {
		return viewPurchaseDetailRepository.findByStatusContainingOrderByOrderNumberDesc(status);
	}
	
	@Override
	public Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size);
		return viewPurchaseDetailRepository.findAllByOrderByOrderNumberDesc(pageable);
	}
	
	@Override
	public Page<ViewPurchaseDetailEntity> getPaginatedDataByStatus(String status, int pno, int size) {
		status = status.equals("wa") ? "입고대기" : "입고완료";
		Pageable pageable = PageRequest.of(pno - 1, size);
		return viewPurchaseDetailRepository.findByStatusContainingOrderByOrderNumberDesc(status, pageable);
	}

}
