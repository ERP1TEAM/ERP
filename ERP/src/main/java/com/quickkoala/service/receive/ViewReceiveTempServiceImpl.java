package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveTempEntity;
import com.quickkoala.repository.receive.ViewReceiveTempRepository;

@Service
public class ViewReceiveTempServiceImpl implements ViewReceiveTempService{
	
	@Autowired
	private ViewReceiveTempRepository viewReceiveTempRepository;
	
	@Override
	public Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewReceiveTempRepository.findAllByOrderByCodeDesc(pageable);
	}
	
	@Override
	public Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewReceiveTempEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("가입고코드")) {
			result = viewReceiveTempRepository.findByCodeContainingOrderByCodeDesc(word, pageable);			
		}else if(code.equals("발주번호")) {
			result = viewReceiveTempRepository.findByOrderNumberContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("제조사")) {
			result = viewReceiveTempRepository.findBySupplierNameContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewReceiveTempRepository.findByProductNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
	}
}
