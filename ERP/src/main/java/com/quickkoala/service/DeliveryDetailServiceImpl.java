package com.quickkoala.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.DeliveryDetailEntity;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.ReceiveTempEntity;
import com.quickkoala.repository.DeliveryDetailRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class DeliveryDetailServiceImpl implements DeliveryDetailService{

	@Autowired
	private DeliveryDetailRepository deliveryDetailRepository;
	
	@Override
	public DeliveryDetailEntity addDelivery(String data, Integer ea) {
		
		int number = (int)this.getCountOfOrdersToday()+1;
		
		DeliveryDetailEntity entity = new DeliveryDetailEntity();
		String formattedNumber = String.format("%03d", number);
		entity.setCode("DT"+TodayUtils.getToday()+"-"+formattedNumber);
		entity.setOrderNumber(data);
		entity.setQuantity(ea);
		entity.setDate(LocalDateTime.now());
		
		return deliveryDetailRepository.save(entity);
	}
	
	@Override
	public long getCountOfOrdersToday() {
		LocalDate today = LocalDate.now();
        return deliveryDetailRepository.countByDate(today);
	}
	
	@Override
	public List<DeliveryDetailEntity> getDelivery() {
		return deliveryDetailRepository.findAllByOrderByCodeDesc();
	}
}
