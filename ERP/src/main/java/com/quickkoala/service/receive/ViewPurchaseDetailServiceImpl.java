package com.quickkoala.service.receive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;
import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.repository.receive.ViewPurchaseDetailRepository;

@Service
public class ViewPurchaseDetailServiceImpl implements ViewPurchaseDetailService{

	@Autowired
	private ViewPurchaseDetailRepository viewPurchaseDetailRepository;
	
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
	
	@Override
	public Page<ViewPurchaseDetailEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewPurchaseDetailEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("발주번호")) {
			result = viewPurchaseDetailRepository.findByOrderNumberContainingOrderByOrderNumberDesc(word, pageable);
		}else if(code.equals("제조사")) {
			result = viewPurchaseDetailRepository.findBySupplierNameContainingOrderByOrderNumberDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewPurchaseDetailRepository.findByProductNameContainingOrderByOrderNumberDesc(word, pageable);
		}
		return result;
	}
	

}
