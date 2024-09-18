package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;
import com.quickkoala.repository.receive.ViewReceiveReturnRepository;

@Service
public class ViewReceiveReturnServiceImpl implements ViewReceiveReturnService{

	@Autowired
	private ViewReceiveReturnRepository viewReceiveReturnRepository;
	
	@Override
	public List<ViewReceiveReturnEntity> getAllData() {
		return viewReceiveReturnRepository.findAllByOrderByReturnDateDesc();
	}
	
	@Override
	public Page<ViewReceiveReturnEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewReceiveReturnRepository.findAllByOrderByReturnDateDesc(pageable);
	}
	
	@Override
	public Page<ViewReceiveReturnEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewReceiveReturnEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("반품번호")) {
			result = viewReceiveReturnRepository.findByReturnNumberContainingOrderByReturnNumberDesc(word, pageable);			
		}else if(code.equals("발주번호")) {
			result = viewReceiveReturnRepository.findByOrderNumberContainingOrderByReturnNumberDesc(word, pageable);
		}else if(code.equals("제조사")) {
			result = viewReceiveReturnRepository.findBySupplierNameContainingOrderByReturnNumberDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewReceiveReturnRepository.findByProductNameContainingOrderByReturnNumberDesc(word, pageable);
		}
		return result;
	}
}
