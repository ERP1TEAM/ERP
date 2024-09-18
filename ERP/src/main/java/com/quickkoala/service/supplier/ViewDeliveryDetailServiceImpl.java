package com.quickkoala.service.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;
import com.quickkoala.repository.supplier.ViewDeliveryDetailRepository;

@Service
public class ViewDeliveryDetailServiceImpl implements ViewDeliveryDetailService{
	
	@Autowired
	private ViewDeliveryDetailRepository viewDeliveryDetailRepository;
	
	@Override
	public Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size);
		return viewDeliveryDetailRepository.findAllByOrderByDeliveryCodeDesc(pageable);
	}
	
	@Override
	public Page<ViewDeliveryDetailEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewDeliveryDetailEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("납품번호")) {
			result = viewDeliveryDetailRepository.findByDeliveryCodeContainingOrderByDeliveryCodeDesc(word, pageable);			
		}else if(code.equals("발주번호")) {
			result = viewDeliveryDetailRepository.findByOrderNumberContainingOrderByDeliveryCodeDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewDeliveryDetailRepository.findByProductNameContainingOrderByDeliveryCodeDesc(word, pageable);
		}
		return result;
	}
}
