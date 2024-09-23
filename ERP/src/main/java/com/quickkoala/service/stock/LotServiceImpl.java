package com.quickkoala.service.stock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.entity.stock.LotEntity;
import com.quickkoala.entity.supplier.PurchaseEntity;
import com.quickkoala.repository.stock.LotRepository;
import com.quickkoala.service.supplier.PurchaseServiceImpl;
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
		LocalDate sevenDaysAgo = today.minusDays(7);
		LocalDateTime todayT = LocalDateTime.now();
		PurchaseEntity ent = purchaseServiceImpl.getOrderByNumber(dto.getOrderNumber());
		Optional<Integer> maxSerialOpt = lotRepository.findMaxSerialNumberByProductCodeAndDate(ent.getProductCode(), today);
		int nextSerial = maxSerialOpt.orElse(0) + 1;
		String serialFormatted = String.format("%03d", nextSerial);
		
		data.setLotNumber(ent.getProductCode()+"-"+TodayUtils.getTodayS()+"-"+serialFormatted);
		data.setProductCode(ent.getProductCode());
		data.setSupplierCode(ent.getSupplierCode());
		data.setQuantity(dto.getReQty());
		data.setProductionDate(sevenDaysAgo);
		data.setReceiveDate(todayT);
		data.setLastUpdate(todayT);
		
		return lotRepository.save(data);
	}
}
