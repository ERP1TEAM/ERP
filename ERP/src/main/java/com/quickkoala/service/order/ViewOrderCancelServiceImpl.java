package com.quickkoala.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<OrderCancelDto> getAll(int pg, int size) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("orderNumber")));
		List<ViewOrderCancelEntity> before = viewOrderCancelRepository.findAll(pageable).getContent();
		List<OrderCancelDto> after = new ArrayList<OrderCancelDto>();
		for(ViewOrderCancelEntity item: before) {
			OrderCancelDto dto = new OrderCancelDto();
			dto.setOrderNumber(item.getOrderNumber());
			dto.setOrderId(item.getOrderId());
			dto.setSalesCode(item.getSalesCode());
			dto.setSalesName(item.getSalesName());
			dto.setDt(item.getDt().toString());
			dto.setManager(item.getManager());
			dto.setMemo(item.getMemo());
			dto.setOrderTotal(item.getOrderTotal());
			after.add(dto);
		}
		return after;
	}
	
}
