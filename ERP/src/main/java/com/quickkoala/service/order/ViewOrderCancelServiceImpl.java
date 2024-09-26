package com.quickkoala.service.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.repository.order.ViewOrderCancelRepository;


@Service
public class ViewOrderCancelServiceImpl implements ViewOrderCancelService{
	
	@Autowired
	private ViewOrderCancelRepository viewOrderCancelRepository;
	
	@Override
	public Page<ViewOrderCancelEntity> getAll(int pg, int size,String select, String param, String startDate, String endDate) {
		try {
		
		if(startDate.equals("null")||endDate.equals("null")||endDate==""||startDate=="") {
			Pageable pageable=(Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("dt")));
			if(select.equals(null)||select.equals("null")||select=="") {
				return viewOrderCancelRepository.findAll(pageable);
			}else if(select.equals("1")&&param!=null) {
				return viewOrderCancelRepository.findByOrderNumberContaining(param,pageable);
			}else if(select.equals("2")&&param!=null) {
				return viewOrderCancelRepository.findBySalesNameContaining(param,pageable);
			}else if(select.equals("3")&&param!=null) {
				return viewOrderCancelRepository.findByManagerContaining(param,pageable);
			}else {
				return Page.empty();
			}
		}else {
			LocalDateTime sd = LocalDate.parse(startDate).atStartOfDay();
			LocalDateTime ed = LocalDate.parse(endDate).atTime(23, 59, 59) ;
		Pageable pageable=(Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("dt")));
		if(select.equals(null)||select.equals("null")||select=="") {
			return viewOrderCancelRepository.findAllByDtBetween(sd,ed,pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewOrderCancelRepository.findByOrderNumberContainingAndDtBetween(param,sd,ed,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewOrderCancelRepository.findBySalesNameContainingAndDtBetween(param,sd,ed,pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewOrderCancelRepository.findByManagerContainingAndDtBetween(param,sd,ed,pageable);
		}else {
			return Page.empty();
		}
		}
		
		}catch(Exception e) {
			return Page.empty();
		}
	}
	
	
	
}
