package com.quickkoala.service.order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.ReleaseCancelEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelReason;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelWho;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.repository.order.OrderCancelRepository;
import com.quickkoala.repository.order.OrderRepository;
import com.quickkoala.repository.release.ReleaseCancelRepository;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.service.release.OrderReleaseService;



@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@Autowired
	private OrderReleaseService orderReleaseService;
	
	@Autowired
	private OrderCancelRepository orderCancelRepository;
	
	
	
	@Override
	public String updateStatus(String id,String status) {
		if(status=="취소") {
			OrderCancelEntity entity = new OrderCancelEntity();
			Optional<OrderEntity> optional = orderRepository.findById(id);
			 if(optional.isPresent()) {
				 entity.setDt(LocalDateTime.now());
				 entity.setManager("김하주");
				 entity.setMemo(null);
				 entity.setOrderId(optional.get().getOrderId());
				 entity.setOrderNumber(optional.get().getNumber());
				 orderCancelRepository.save(entity);
			 }
		}else {
			
		}
		return (orderRepository.updateStatus(OrderEntity.OrderStatus.valueOf(status),id)>0)?"OK":"NO";
	}
	
	@Override
	public String updateApproved(String id) {
		 Optional<OrderEntity> optional = orderRepository.findById(id);
		 String result = "NO";
		 if(optional.isPresent()) {
			 OrderEntity order = optional.get();
			 order.setStatus(OrderStatus.승인);
			 order.setApprovedDt(orderRepository.now());
			 orderRepository.save(order);
			 OrderReleaseEntity entity = new OrderReleaseEntity();
			 entity.setOrderId(order.getOrderId());
			 entity.setManager("김하주");
			 entity.setDt(orderRepository.now() );
			 entity.setMemo(null);
			 entity.setOrderNumber(order.getNumber());
			 entity.setSalesCode(order.getSalesCode());
			 orderReleaseService.addReleaseFromOrder(entity);
			 result="OK";
			 
			
			 
		 }
		return result;
	}
	
}
