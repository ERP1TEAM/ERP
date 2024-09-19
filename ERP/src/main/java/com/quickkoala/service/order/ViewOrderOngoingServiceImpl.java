package com.quickkoala.service.order;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.order.OrderOngoingDto;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.repository.order.ViewOrderOngoingRepository;


@Service
public class ViewOrderOngoingServiceImpl implements ViewOrderOngoingService{
	
	@Autowired
	private ViewOrderOngoingRepository viewOrderOngoingRepository;
	
	
	@Override
	public List<OrderOngoingDto> getAll(int pg, int size) {
		OrderStatus status = OrderStatus.미승인;
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
		List<ViewOrderOngoingEntity> before = viewOrderOngoingRepository.findAllByStatus(status,pageable).getContent();
		List<OrderOngoingDto> after = new ArrayList<OrderOngoingDto>();
		for(ViewOrderOngoingEntity item: before) {
			OrderOngoingDto dto = new OrderOngoingDto();
			dto.setNumber(item.getNumber());
			dto.setOrderId(item.getOrderId());
			dto.setStatus(item.getStatus().toString());
			dto.setSalesCode(item.getSalesCode());
			dto.setSalesName(item.getSalesName());
			dto.setDt(item.getDt().toString());
			dto.setApprovedDt(item.getApprovedDt() != null ? item.getApprovedDt().toString():"null");
			dto.setManager(item.getManager());
			dto.setMemo(item.getMemo());
			dto.setOrderTotal(item.getOrderTotal());
			after.add(dto);
		}
		return after;
	}
	
	
}
