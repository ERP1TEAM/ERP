package com.quickkoala.service.supplier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.dto.receive.PurchaseDto;
import com.quickkoala.entity.supplier.PurchaseEntity;
import com.quickkoala.entity.supplier.SupplierEntity;
import com.quickkoala.repository.supplier.PurchaseRepository;
import com.quickkoala.repository.supplier.SupplierRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class PurchaseServiceImpl implements PurchaseService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private SupplierService supplierService;
	
	@Override
	public List<PurchaseEntity> addOrders(PurchaseListDto orders) {
		List<PurchaseEntity> orderEntities = new ArrayList<>();
		int number = (int)this.getCountOfOrdersToday()+1;
		
	    for (int f=0; f<orders.getProduct_code().size(); f++) {
	        PurchaseEntity order = new PurchaseEntity();
	        String formattedNumber = String.format("%03d", number);
	        order.setOrderNumber(TodayUtils.getToday()+"-" + formattedNumber);
	        order.setSupplierCode(supplierService.getCode(orders.getSupplier().get(f)).getCode());
	        order.setProductCode(orders.getProduct_code().get(f));
	        order.setManager("홍길동");
	        order.setQuantity(orders.getQuantity().get(f));
	        order.setPrice(orders.getPrice().get(f));
	        order.setTotalPrice(orders.getQuantity().get(f) * orders.getPrice().get(f));
	        if (order.getOrderDate() == null) {
	            order.setOrderDate(LocalDateTime.now());
	        }
	        order.setExpectedDate(TodayUtils.getSevenDaysAfter());
	        order.setStatus("입고대기");
	        orderEntities.add(order);
	        number += 1;
	    }

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
	
	@Override
	public List<PurchaseEntity> getAllOrdersByStatus(String status) {
		if(status == null) {
			return purchaseRepository.findAllByOrderByOrderNumberDesc();
		}else {
			return purchaseRepository.findAllByStatusOrderByOrderNumberDesc(status);			
		}
	}
	
	@Override
	public PurchaseEntity getOrderByNumber(String number) {
		return purchaseRepository.findByOrderNumber(number);
	}
}
