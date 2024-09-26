package com.quickkoala.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.repository.order.OrderCancelRepository;
import com.quickkoala.repository.order.OrderRepository;


@Service
public class OrderCancelServiceImpl implements OrderCancelService{
	
	@Autowired
	private OrderCancelRepository orderCancelRepository;
	
	
}
