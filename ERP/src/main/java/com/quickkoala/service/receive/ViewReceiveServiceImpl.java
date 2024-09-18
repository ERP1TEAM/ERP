package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.repository.receive.ViewReceiveRepository;

@Service
public class ViewReceiveServiceImpl implements ViewReceiveService {

	@Autowired
	private ViewReceiveRepository viewReceiveRepository;
	
	@Override
	public List<ViewReceiveEntity> getData() {
		return viewReceiveRepository.findAllByOrderByReceiveCodeDesc();
	}
	@Override
	public long getCount() {
		return viewReceiveRepository.count();
	}
	@Override
	public Page<ViewReceiveEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size); // 페이지 번호는 0부터 시작하므로 pno - 1
        return viewReceiveRepository.findAllByOrderByReceiveCodeDesc(pageable);
	}
	
	@Override
	public Page<ViewReceiveEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewReceiveEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("입고번호")) {
			result = viewReceiveRepository.findByReceiveCodeContainingOrderByReceiveCodeDesc(word, pageable);			
		}else if(code.equals("발주번호")) {
			result = viewReceiveRepository.findByOrderNumberContainingOrderByReceiveCodeDesc(word, pageable);
		}else if(code.equals("제조사")) {
			result = viewReceiveRepository.findBySupplierNameContainingOrderByReceiveCodeDesc(word, pageable);
		}else if(code.equals("상품명")) {
			result = viewReceiveRepository.findByProductNameContainingOrderByReceiveCodeDesc(word, pageable);
		}
		return result;
	}
	
}
