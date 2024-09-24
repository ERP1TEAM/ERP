package com.quickkoala.service.supplier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.receive.ViewReceiveEntity;
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
	
	@Override
	public Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size, SearchDto dto) {
		Page<ViewPurchaseSummaryEntity> result = null;
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
	    
		result = viewPurchaseSummaryRepository.search(searchField, codeType, sDateTime, eDateTime, pageable);
		return result;
	}
}
