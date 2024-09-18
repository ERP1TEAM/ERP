package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.repository.receive.ViewReceiveSummaryRepository;

@Service
public class ViewReceiveSummaryServiceImpl implements ViewReceiveSummaryService{
	@Autowired
	private ViewReceiveSummaryRepository viewReceiveSummaryRepository;
	
	@Override
	public ViewReceiveSummaryEntity getOneData(String orderNumber) {
		return viewReceiveSummaryRepository.findByOrderNumber(orderNumber);
	}
	
	@Override
	public Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewReceiveSummaryRepository.findAllByOrderByOrderNumberDesc(pageable);
	}
	
	@Override
	public Page<ViewReceiveSummaryEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewReceiveSummaryEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("발주번호")) {
			result = viewReceiveSummaryRepository.findByOrderNumberContainingOrderByOrderNumberDesc(word, pageable);			
		}else if(code.equals("발주품목")) {
			result = viewReceiveSummaryRepository.findByProductNameContainingOrderByOrderNumberDesc(word, pageable);
		}
		return result;
	}
}
