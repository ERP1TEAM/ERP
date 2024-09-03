package com.quickkoala.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	public PurchaseEntity addAllOrder(PurchaseDto orders) {
		PurchaseEntity order = new PurchaseEntity();
		//order.setOrderNumber("20240903-"+this.getCountOfOrdersToday()+1);
		order.setOrderNumber("20240903-10");
		order.setSupplierCode(orders.getSupplier());
		order.setProductCode(orders.getProduct_code());
		order.setManager("홍길동");
		order.setQuantity(orders.getQuantity());
		order.setPrice(orders.getPrice());
		order.setTotalPrice(orders.getQuantity()*orders.getPrice());
		if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
		order.setExpectedDate("2024-09-10");
		order.setStatus("입고대기");
//		return purchaseRepository.saveAll(order);
		return purchaseRepository.save(order);
	}
	
	@Override
	public List<PurchaseEntity> addOrder(PurchaseListDto orders) {
		System.out.println("1번까진됨");
		List<PurchaseEntity> orderEntities = new ArrayList<>();
		int number = (int)this.getCountOfOrdersToday()+1;
		
		System.out.println("2번까진됨");
		
	    for (int f=0; f<orders.getProduct_code().size(); f++) {
	        PurchaseEntity order = new PurchaseEntity();
	        String formattedNumber = String.format("%02d", number);
	        order.setOrderNumber("20240903-" + formattedNumber);
	        order.setSupplierCode(orders.getSupplier().get(f));
	        order.setProductCode(orders.getProduct_code().get(f));
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

	    System.out.println("3번까진됨");
	    // 여러 엔티티를 저장하므로 saveAll 사용
	    return purchaseRepository.saveAll(orderEntities);
	}
	
	public long getCountOfOrdersToday() {
        LocalDate today = LocalDate.now();
        return purchaseRepository.countByOrderDate(today);
    }
	
	@Override
	public List<PurchaseEntity> getAllOrders() {
		return purchaseRepository.findAllByOrderByOrderNumberDesc();
	}
}
