package com.quickkoala.service.release;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.repository.release.ViewReleaseCancelRepository;

@Service
public class ViewReleaseCancelServiceImpl implements ViewReleaseCancelService{
	
	@Autowired
	private ViewReleaseCancelRepository viewReleaseCancelRepository;
	
	public Page<ViewReleaseCancelEntity> getAll(int pg, int size,String select, String param){
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseCancelRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseCancelRepository.findByRelNumberContainingOrderByRelNumberDesc(param, pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseCancelRepository.findByReasonContainingOrderByRelNumberDesc(param, pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewReleaseCancelRepository.findBySalesNameContainingOrderByRelNumberDesc(param, pageable);
		}else {
			return Page.empty();
		}
		
	}
	
}