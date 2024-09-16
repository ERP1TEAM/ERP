package com.quickkoala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewDeliveryReturnEntity;
import com.quickkoala.repository.ViewDeliveryReturnRepository;

@Service
public class ViewDeliveryReturnServiceImpl implements ViewDeliveryReturnService{
	
	@Autowired
	private ViewDeliveryReturnRepository viewDeliveryReturnRepository;
	
	@Override
	public Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewDeliveryReturnRepository.findAllByOrderByReturnDateDesc(pageable);
	}
}
