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
	
	@Override
	public Page<ViewReceiveEntity> getPaginatedData(int pno, int size, SearchDto dto) {
		Page<ViewReceiveEntity> result = null;
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
	    
		result = viewReceiveRepository.search(searchField, codeType, sDateTime, eDateTime, pageable);
		return result;
	}
	
}
