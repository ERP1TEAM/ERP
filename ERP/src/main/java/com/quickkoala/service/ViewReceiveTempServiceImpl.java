package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewReceiveTempEntity;
import com.quickkoala.repository.ViewReceiveTempRepository;

@Service
public class ViewReceiveTempServiceImpl implements ViewReceiveTempService{
	
	@Autowired
	private ViewReceiveTempRepository viewReceiveTempRepository;
	
	@Override
	public List<ViewReceiveTempEntity> getAllOrders(String status) {
		if(status == null) {
			return viewReceiveTempRepository.findAllByOrderByCodeDesc();			
		}else if(status.equals("처리대기")) {
			return viewReceiveTempRepository.findByWtQuantityNotOrderByCodeDesc(0);
		}else if(status.equals("처리완료")) {
			return viewReceiveTempRepository.findByWtQuantityOrderByCodeDesc(0);
		}
		return viewReceiveTempRepository.findAllByOrderByCodeDesc();
	}
	
	@Override
	public Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewReceiveTempRepository.findAllByOrderByCodeDesc(pageable);
	}
}
