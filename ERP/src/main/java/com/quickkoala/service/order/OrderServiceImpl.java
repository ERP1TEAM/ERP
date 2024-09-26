package com.quickkoala.service.order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseCancelEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelReason;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelWho;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.repository.order.OrderCancelRepository;
import com.quickkoala.repository.order.OrderRepository;
import com.quickkoala.repository.release.ReleaseCancelRepository;
import com.quickkoala.repository.release.ReleaseProductsRepository;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.service.release.OrderReleaseService;

import jakarta.transaction.Transactional;



@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@Autowired
	private OrderReleaseService orderReleaseService;
	
	@Autowired
	private OrderCancelRepository orderCancelRepository;
	
	@Autowired
	private ReleaseProductsRepository releaseProductsRepository;
	
	@Autowired ClientsOrderProductsRepository clientsOrderProductsRepository;
	
	@Override
	@Transactional
	public String updateStatus(String orderNumber,String status,String manager) {
		 Optional<OrderEntity> optional = orderRepository.findById(orderNumber);
		 String result = "NO";
		if(status=="취소") {
			OrderCancelEntity entity = new OrderCancelEntity();
			 if(optional.isPresent()) {
				 entity.setDt(LocalDateTime.now());
				 entity.setManager(manager);
				 entity.setMemo(null);
				 entity.setTotalPrice(optional.get().getOrderTotal());
				 entity.setOrderId(optional.get().getOrderId());
				 entity.setOrderNumber(optional.get().getNumber());
				 entity.setSalesCode(optional.get().getSalesCode());
				 OrderCancelEntity saved= orderCancelRepository.save(entity);
				 if(saved!=null) {
					 result="OK";
				 }
				 orderRepository.delete(optional.get());
			 }
		}else if(status=="승인") {
			 if(optional.isPresent()) {
				 OrderEntity order = optional.get();
				 OrderReleaseEntity entity = new OrderReleaseEntity();
				 entity.setOrderId(order.getOrderId());
				 entity.setManager(manager);
				 entity.setDt(LocalDateTime.now());
				 entity.setMemo(null);
				 entity.setOrderNumber(order.getNumber());
				 entity.setSalesCode(order.getSalesCode());
				 if(orderReleaseService.addReleaseFromOrder(entity)=="OK"){
					 order.setStatus(OrderStatus.승인);
					 order.setApprovedDt(LocalDateTime.now());
					 orderRepository.save(order);
					 result="OK";
				 }else {
					 result="NO";
				 }
			 }
		}
		return result;
	}
	

	
}
