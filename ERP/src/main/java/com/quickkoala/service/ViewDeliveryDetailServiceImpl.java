package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewDeliveryDetailEntity;
import com.quickkoala.repository.ViewDeliveryDetailRepository;

@Service
public class ViewDeliveryDetailServiceImpl implements ViewDeliveryDetailService{
	
	@Autowired
	private ViewDeliveryDetailRepository viewDeliveryDetailRepository;
	
	@Override
	public List<ViewDeliveryDetailEntity> getAllData() {
		return viewDeliveryDetailRepository.findAllByOrderByDeliveryCodeDesc();
	}
	
	@Override
	public Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size);
		return viewDeliveryDetailRepository.findAll(pageable);
	}
}
