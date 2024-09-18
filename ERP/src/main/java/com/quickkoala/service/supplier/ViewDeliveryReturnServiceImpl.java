package com.quickkoala.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;
import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;
import com.quickkoala.repository.supplier.ViewDeliveryReturnRepository;

@Service
public class ViewDeliveryReturnServiceImpl implements ViewDeliveryReturnService{
	
	@Autowired
	private ViewDeliveryReturnRepository viewDeliveryReturnRepository;
	
	@Override
	public Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewDeliveryReturnRepository.findAllByOrderByReturnDateDesc(pageable);
	}
	
	@Override
	public Page<ViewDeliveryReturnEntity> getPaginadtedData(int pno, int size, String code, String word) {
		Page<ViewDeliveryReturnEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("납품번호")) {
			result = viewDeliveryReturnRepository.findByDeliveryCodeContainingOrderByReturnDateDesc(word, pageable);			
		}else if(code.equals("발주번호")) {
			result = viewDeliveryReturnRepository.findByOrderNumberContainingOrderByReturnDateDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewDeliveryReturnRepository.findByProductNameContainingOrderByReturnDateDesc(word, pageable);
		}
		return result;
	}
}
