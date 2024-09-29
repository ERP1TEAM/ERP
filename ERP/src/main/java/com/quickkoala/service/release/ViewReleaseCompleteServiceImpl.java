package com.quickkoala.service.release;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;
import com.quickkoala.repository.release.ViewReleaseCompleteRepository;

@Service
public class ViewReleaseCompleteServiceImpl implements ViewReleaseCompleteService{
	
	@Autowired
	private ViewReleaseCompleteRepository viewReleaseCompleteRepository;
	
	@Override
	public Page<ViewReleaseCompleteEntity> getAll(int pg, int size,String select, String param,String startDate,String endDate) {
		try {
			
			if(startDate.equals("null")||endDate.equals("null")||endDate==""||startDate=="") {
		
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseCompleteRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseCompleteRepository.findByRelNumberContaining(param,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseCompleteRepository.findByOrderNumberContaining(param,pageable);
			
		}else if(select.equals("3")&&param!=null) { 
			return viewReleaseCompleteRepository.findBySalesNameContaining(param,pageable);
			
		}else{
			return Page.empty();
		}
		
			}else {
				LocalDateTime sd = LocalDate.parse(startDate).atStartOfDay();
				LocalDateTime ed = LocalDate.parse(endDate).atTime(23, 59, 59) ;
				Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
				if(select.equals(null)||select.equals("null")) {
					return viewReleaseCompleteRepository.findAllByDtBetween(sd,ed,pageable);
				}else if(select.equals("1")&&param!=null) {
					return viewReleaseCompleteRepository.findByRelNumberContainingAndDtBetween(param,sd,ed,pageable);
				}else if(select.equals("2")&&param!=null) {
					return viewReleaseCompleteRepository.findByOrderNumberContainingAndDtBetween(param,sd,ed,pageable);
					
				}else if(select.equals("3")&&param!=null) { 
					return viewReleaseCompleteRepository.findBySalesNameContainingAndDtBetween(param,sd,ed,pageable);
					
				}else{
					return Page.empty();
				}
			}
		}catch(Exception e) {
			return Page.empty();
		}
			
	}
}