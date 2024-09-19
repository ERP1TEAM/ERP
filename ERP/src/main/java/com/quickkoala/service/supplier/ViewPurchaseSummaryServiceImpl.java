package com.quickkoala.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;
import com.quickkoala.repository.supplier.ViewPurchaseSummaryRepository;

@Service
public class ViewPurchaseSummaryServiceImpl implements ViewPurchaseSummaryService{

	@Autowired
	private ViewPurchaseSummaryRepository viewPurchaseSummaryRepository;
	
	@Override
	public Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(0, pageable);
	}
	@Override
	public Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size, String code, String word) {
		Page<ViewPurchaseSummaryEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("발주번호")) {
			result = viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanAndOrderNumberContainingOrderByOrderNumberDesc(0, word, pageable);
		}else if(code.equals("상품명")) {
			result = viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanAndProductNameContainingOrderByOrderNumberDesc(0, word, pageable);
		}
		return result;
	}
}
