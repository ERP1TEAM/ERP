package com.quickkoala.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.repository.order.ViewOrderOngoingRepository;


@Service
public class ViewOrderOngoingServiceImpl implements ViewOrderOngoingService{
	
	@Autowired
	private ViewOrderOngoingRepository viewOrderOngoingRepository;
	
	@Override
	public Page<ViewOrderOngoingEntity> getAll(int pg, int size,String select, String param) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
		
		if(select.equals(null)||select.equals("null")) {
			return viewOrderOngoingRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewOrderOngoingRepository.findByNumberContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewOrderOngoingRepository.findBySalesNameContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewOrderOngoingRepository.findByManagerContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("4")&&param!=null) {
			  try {
			        OrderStatus status = OrderStatus.valueOf(param.intern());
			        Page<ViewOrderOngoingEntity> temp=viewOrderOngoingRepository.findByStatus(status, pageable);
			        return temp;
			    } catch (IllegalArgumentException e) {
			        return Page.empty(); 
			    }
		}else {
			return Page.empty();
		}
	}
	
	
	
	
}
