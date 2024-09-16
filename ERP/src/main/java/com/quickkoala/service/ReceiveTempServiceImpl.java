package com.quickkoala.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.SupplierDeliveryListDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.ReceiveTempEntity;
import com.quickkoala.repository.PurchaseRepository;
import com.quickkoala.repository.ReceiveTempRepository;
import com.quickkoala.utils.TodayUtils;

import jakarta.transaction.Transactional;

@Service
public class ReceiveTempServiceImpl implements ReceiveTempService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ReceiveTempRepository receiveTempRepository;
	
	@Autowired
	private DeliveryDetailService deliveryDetailService;
	
	@Override
	public List<ReceiveTempEntity> addAllReceive(SupplierDeliveryListDto orders) {
		List<ReceiveTempEntity> orderEntities = new ArrayList<>();
		
		
	    /*for (int f=0; f<orders.getProduct_code().size(); f++) {
	    	ReceiveTempEntity order = new ReceiveTempEntity();
	        String formattedNumber = String.format("%02d", number);
	        order.setCode("20240903-" + formattedNumber);
	        order.setOrderNumber(orders.get);
	        order.setManager("홍길동");
	        order.setQuantity(orders.getQuantity().get(f));
	        order.setPrice(orders.getPrice().get(f));
	        order.setTotalPrice(orders.getQuantity().get(f) * orders.getPrice().get(f));
	        if (order.getOrderDate() == null) {
	            order.setOrderDate(LocalDateTime.now());
	        }
	        order.setExpectedDate("2024-09-10");
	        order.setStatus("입고대기");
	        orderEntities.add(order);
	        number += 1;
	    }
		*/
	    System.out.println("3번까진됨");
	    // 여러 엔티티를 저장하므로 saveAll 사용
	    return receiveTempRepository.saveAll(orderEntities);
	}
	
	@Override
	public ReceiveTempEntity addDelivery(String data, Integer ea, String code) {
		int number = (int)this.getCountOfOrdersToday()+1;
		
		PurchaseEntity purchaseEntity = purchaseRepository.findByOrderNumber(data);
		ReceiveTempEntity receiveTempEntity = new ReceiveTempEntity();
		String formattedNumber = String.format("%03d", number);
		receiveTempEntity.setCode("RT"+TodayUtils.getToday()+"-"+formattedNumber);
		receiveTempEntity.setOrderNumber(purchaseEntity.getOrderNumber());
		receiveTempEntity.setDeliveryCode(code);
		receiveTempEntity.setQuantity(purchaseEntity.getQuantity());
		receiveTempEntity.setWtQuantity(ea);
		receiveTempEntity.setDate(LocalDateTime.now());
		receiveTempEntity.setMemo("-");
		
		return receiveTempRepository.save(receiveTempEntity);
	}
	
	@Override
	public long getCountOfOrdersToday() {
		LocalDate today = LocalDate.now();
        return receiveTempRepository.countByOrderDate(today);
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
	public ReceiveTempEntity modifyStatus(String data, Integer ea) {
		return null;
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
