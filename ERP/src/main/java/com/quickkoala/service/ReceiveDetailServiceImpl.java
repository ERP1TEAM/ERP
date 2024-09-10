package com.quickkoala.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ReceiveDetailEntity;
import com.quickkoala.repository.ReceiveDetailRepository;
import com.quickkoala.repository.ReceiveTempRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class ReceiveDetailServiceImpl implements ReceiveDetailService{
	
	@Autowired
	private ReceiveDetailRepository receiveDetailRepository;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Override
	public ReceiveDetailEntity addData(String data, Integer ea) {
		ReceiveDetailEntity receiveDetailEntity = new ReceiveDetailEntity();
		String orderNumber = receiveTempService.getOrderNumber(data);
		
		int number = (int)this.getCountOfOrdersToday()+1;
		String formattedNumber = String.format("%03d", number);
		receiveDetailEntity.setCode("R"+TodayUtils.getToday()+"-" + formattedNumber);
		receiveDetailEntity.setOrderNumber(orderNumber);
		receiveDetailEntity.setQuantity(ea);
		receiveDetailEntity.setDate(LocalDateTime.now());
		return receiveDetailRepository.save(receiveDetailEntity);
	}
	
	@Override
	public long getCountOfOrdersToday() {
		LocalDate today = LocalDate.now();
		return receiveDetailRepository.countByCode(today);
	}
	
}
