package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.DeliveryDetailViewEntity;
import com.quickkoala.repository.DeliveryDetailViewRepository;

@Service
public class DeliveryDetailViewServiceImpl implements DeliveryDetailViewService{
	
	@Autowired
	private DeliveryDetailViewRepository deliveryDetailViewRepository;
	
	@Override
	public List<DeliveryDetailViewEntity> getAllData() {
		return deliveryDetailViewRepository.findAllByOrderByDeliveryCodeDesc();
	}
}
