package com.quickkoala.service.release;

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
	public Page<ViewReleaseCompleteEntity> getAll(int pg, int size,String select, String param) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseCompleteRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseCompleteRepository.findByRelNumberContainingOrderByRelNumberDesc(param,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseCompleteRepository.findByOrderNumberContainingOrderByRelNumberDesc(param,pageable);
			
		}else if(select.equals("3")&&param!=null) { 
			return viewReleaseCompleteRepository.findBySalesNameContainingOrderByRelNumberDesc(param,pageable);
			
		}else{
			return Page.empty();
		}
	}
}