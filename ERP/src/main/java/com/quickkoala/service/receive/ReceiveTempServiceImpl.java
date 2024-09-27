package com.quickkoala.service.receive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ReceiveTempEntity;
import com.quickkoala.entity.supplier.PurchaseEntity;
import com.quickkoala.repository.receive.ReceiveTempRepository;
import com.quickkoala.repository.supplier.PurchaseRepository;
import com.quickkoala.utils.TodayUtils;

import jakarta.transaction.Transactional;

@Service
public class ReceiveTempServiceImpl implements ReceiveTempService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ReceiveTempRepository receiveTempRepository;
	
	@Override
	public ReceiveTempEntity addDelivery(String data, Integer ea, String code) {
		PurchaseEntity purchaseEntity = purchaseRepository.findByOrderNumber(data);
		ReceiveTempEntity receiveTempEntity = new ReceiveTempEntity();
		if(this.getMaxCode() != null) {
			String numberStr = this.getMaxCode().substring(this.getMaxCode().lastIndexOf("-") + 1);
			int number = Integer.parseInt(numberStr) + 1;
			String formattedNumber = String.format("%03d", number);
			receiveTempEntity.setCode("RT"+TodayUtils.getToday()+"-"+formattedNumber);			
		}else {
			receiveTempEntity.setCode("RT"+TodayUtils.getToday()+"-"+"001");
		}
		receiveTempEntity.setOrderNumber(purchaseEntity.getOrderNumber());
		receiveTempEntity.setDeliveryCode(code);
		receiveTempEntity.setQuantity(purchaseEntity.getQuantity());
		receiveTempEntity.setWtQuantity(ea);
		receiveTempEntity.setDate(LocalDateTime.now());
		receiveTempEntity.setMemo("-");
		
		return receiveTempRepository.save(receiveTempEntity);
	}
	
	@Override
	public String getMaxCode() {
		LocalDate today = LocalDate.now();
		return receiveTempRepository.findMaxCodeByOrderDate(today);
	}
	
	@Override
	public List<ReceiveTempEntity> getAllTemp() {
		return receiveTempRepository.findAllByOrderByCodeDesc();
	}
	
	@Override
	public ReceiveTempEntity getOne(String data) {
		return receiveTempRepository.findByCode(data);
	}
	
	@Override
	public Integer getWtQuantity(String order) {
		return receiveTempRepository.findTotalWtQuantityByOrderNumber(order);
	}
	
	@Override
	public String getOrderNumber(String data) {
		return receiveTempRepository.findOrderNumberByCode(data);
	}
	
	@Transactional
	@Override
	public void removeData(String code) {
		receiveTempRepository.deleteByCode(code);
	}
}
