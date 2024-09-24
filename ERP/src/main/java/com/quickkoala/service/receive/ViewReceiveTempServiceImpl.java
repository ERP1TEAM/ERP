package com.quickkoala.service.receive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;
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
	
	@Override
	public Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size, SearchDto dto) {
		Page<ViewReceiveTempEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		String sDateString = dto.getSDate();
	    String eDateString = dto.getEDate();
	    LocalDateTime sDateTime = null;
	    LocalDateTime eDateTime = null;
	    String codeType = null;
	    String searchField = null;

	    // 시작 날짜 변환
	    if (sDateString != null && !sDateString.isEmpty()) {
	        // LocalDate로 변환 후 LocalDateTime으로 변환 (시간은 00:00:00으로 설정)
	        LocalDate sDate = LocalDate.parse(sDateString); 
	        sDateTime = sDate.atStartOfDay(); // LocalDateTime으로 변환
	    }

	    // 종료 날짜 변환
	    if (eDateString != null && !eDateString.isEmpty()) {
	        // LocalDate로 변환 후 LocalDateTime으로 변환 (시간은 23:59:59로 설정)
	        LocalDate eDate = LocalDate.parse(eDateString);
	        eDateTime = eDate.atTime(23, 59, 59); // LocalDateTime으로 변환
	    }
	    
	    if(!dto.getWord().equals("")) {
	    	codeType = dto.getCode();
	    	searchField = dto.getWord();
	    }
	    
		result = viewReceiveTempRepository.search(searchField, codeType, sDateTime, eDateTime, pageable);
		return result;
	}
}
