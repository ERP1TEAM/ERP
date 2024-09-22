package com.quickkoala.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.order.OrderCancelDto;
import com.quickkoala.dto.order.OrderOngoingDto;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.repository.order.ViewOrderCancelRepository;


@Service
public class ViewOrderCancelServiceImpl implements ViewOrderCancelService{
	
	@Autowired
	private ViewOrderCancelRepository viewOrderCancelRepository;
	
	@Override
	public Page<ViewOrderCancelEntity> getAll(int pg, int size,Integer select, String param) {
		Pageable pageable=(Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("orderNumber")));
		List<ViewOrderCancelEntity> before = null;
		if(select.equals(null)||select.equals("null")) {
			return viewOrderCancelRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewOrderCancelRepository.findByOrderNumberContainingOrderByOrderNumberDesc(param,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewOrderCancelRepository.findBySalesNameContainingOrderByOrderNumberDesc(param,pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewOrderCancelRepository.findByManagerContainingOrderByOrderNumberDesc(param,pageable);
		}else {
			return Page.empty();
		}
	}
	
}