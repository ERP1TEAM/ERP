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

import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.repository.order.ViewOrderOngoingRepository;


@Service
public class ViewOrderOngoingServiceImpl implements ViewOrderOngoingService{
	
	@Autowired
	private ViewOrderOngoingRepository viewOrderOngoingRepository;
	
	@Override
	public Page<ViewOrderOngoingEntity> getAll(int pg, int size,String select, String param,String startDate,String endDate) {
		OrderStatus[] sts = {OrderStatus.미승인,OrderStatus.승인};
		try {
		if(startDate.equals("null")||endDate.equals("null")||endDate==""||startDate=="") {
			Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
			if(select.equals(null)||select.equals("null")||select=="") {
				return viewOrderOngoingRepository.findAll(pageable);
			}else if(select.equals("1")&&param!=null) {
				return viewOrderOngoingRepository.findByNumberContainingAndStatusIn(param,sts,pageable);
			}else if(select.equals("2")&&param!=null) {
				return viewOrderOngoingRepository.findBySalesNameContainingAndStatusIn(param,sts,pageable);
			}else if(select.equals("3")&&param!=null) {
				return viewOrderOngoingRepository.findByManagerContainingAndStatusIn(param,sts,pageable);
			}
			else if(select.equals("4")&&param!=null) {
				return viewOrderOngoingRepository.findAllByStatus(OrderStatus.valueOf(param),pageable);
			}
			else {
				return Page.empty();
			}
			
		}else {
			LocalDateTime sd = LocalDate.parse(startDate).atStartOfDay();
			LocalDateTime ed = LocalDate.parse(endDate).atTime(23, 59, 59) ;
			Pageable pageable=(Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
			if(select.equals(null)||select.equals("null")||select=="") {
				return viewOrderOngoingRepository.findAllByDtBetweenAndStatusIn(sd,ed,sts,pageable);
			}else if(select.equals("1")&&param!=null) {
				return viewOrderOngoingRepository.findByNumberContainingAndDtBetweenAndStatusIn(param,sd,ed,sts,pageable);
			}else if(select.equals("2")&&param!=null) {
				return viewOrderOngoingRepository.findBySalesNameContainingAndDtBetweenAndStatusIn(param,sd,ed,sts,pageable);
			}else if(select.equals("3")&&param!=null) {
				return viewOrderOngoingRepository.findByManagerContainingAndDtBetweenAndStatusIn(param,sd,ed,sts,pageable);
			}
			else if(select.equals("4")&&param!=null) {
				return viewOrderOngoingRepository.findAllByStatusAndDtBetween(OrderStatus.valueOf(param),sd,ed,pageable);
			}else {
				return Page.empty();
			}
			
			
			
		}
		}catch(Exception e) {
			return Page.empty();
		}
		
	}
	
	
	
	
}
