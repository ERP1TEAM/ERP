package com.quickkoala.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.LotEntity;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.repository.LotRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class LotServiceImpl implements LotService{
	
	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private PurchaseServiceImpl purchaseServiceImpl;
	
	
	@Override
	public LotEntity addLot(ReceivingDto dto) {
		LotEntity data = new LotEntity();
		LocalDate today = LocalDate.now();
		PurchaseEntity ent = purchaseServiceImpl.getOrderByNumber(dto.getOrderNumber());
		Optional<Integer> maxSerialOpt = lotRepository.findMaxSerialNumberByProductCodeAndDate(ent.getProductCode(), today);
		int nextSerial = maxSerialOpt.orElse(0) + 1;
		String serialFormatted = String.format("%03d", nextSerial);
		data.setLotNumber(ent.getProductCode()+"-"+TodayUtils.getTodayS()+"-"+serialFormatted);
		data.setProductCode(ent.getProductCode());
		data.setQuantity(dto.getReQty());
		data.setReceiveDate(LocalDateTime.now());
		return lotRepository.save(data);
	}
	
	@Override
	public long getCountOfOrdersToday() {
		LocalDate today = LocalDate.now();
		
		return lotRepository.countByOrderDate(today);
	}
}
