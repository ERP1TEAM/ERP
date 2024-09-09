package com.quickkoala.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.PurchaseProductViewDto;
import com.quickkoala.entity.PurchaseProductViewEntity;
import com.quickkoala.repository.PurchaseProductViewRepository;

@Service
public class PurchaseProductViewServiceImpl implements PurchaseProductViewService{

	@Autowired
	private PurchaseProductViewRepository purchaseProductViewRepository;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Override
	public List<PurchaseProductViewDto> getAllOrders() {
		List<PurchaseProductViewEntity> result = purchaseProductViewRepository.findAllByOrderByOrderNumberDesc();
		List<PurchaseProductViewDto> data = new ArrayList<>();
		for(PurchaseProductViewEntity ppve : result) {
			PurchaseProductViewDto dto = new PurchaseProductViewDto();
			dto.setOrderNumber(ppve.getOrderNumber());
			dto.setProductCode(ppve.getProductCode());
			dto.setName(ppve.getName());
			dto.setQuantity(String.format("%,d", ppve.getQuantity()));
			dto.setPrice(String.format("%,d", ppve.getPrice()));
			dto.setTotalPrice(String.format("%,d", ppve.getTotalPrice()));
			dto.setOrderDate(String.valueOf(ppve.getOrderDate()).replace("T", " "));
			Integer wtQuantity = receiveTempService.getWtQuantity(ppve.getOrderNumber());
			dto.setWtQuantity(wtQuantity == null ? "0" : String.valueOf(ppve.getQuantity() - wtQuantity));
			data.add(dto);
		}
		return data;
	}
}
