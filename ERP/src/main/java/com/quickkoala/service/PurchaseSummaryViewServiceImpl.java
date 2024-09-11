package com.quickkoala.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.PurchaseSummaryViewDto;
import com.quickkoala.entity.PurchaseSummaryViewEntity;
import com.quickkoala.entity.ReceiveSummaryEntity;
import com.quickkoala.repository.PurchaseSummaryViewRepository;
import com.quickkoala.repository.ReceiveSummaryViewRepository;

@Service
public class PurchaseSummaryViewServiceImpl implements PurchaseSummaryViewService{

	@Autowired
	private PurchaseSummaryViewRepository purchaseProductViewRepository;
	
	@Override
	public List<PurchaseSummaryViewDto> getAllOrders() {
		List<PurchaseSummaryViewEntity> result = purchaseProductViewRepository.findAllByOrderByOrderNumberDesc();
		List<PurchaseSummaryViewDto> data = new ArrayList<>();
		for(PurchaseSummaryViewEntity ppve : result) {
			PurchaseSummaryViewDto dto = new PurchaseSummaryViewDto();
			dto.setOrderNumber(ppve.getOrderNumber());
			dto.setProductCode(ppve.getProductCode());
			dto.setName(ppve.getProductName());
			dto.setQuantity(String.format("%,d", ppve.getQuantity()));
			dto.setPrice(String.format("%,d", ppve.getPrice()));
			dto.setTotalPrice(String.format("%,d", ppve.getTotalPrice()));
			dto.setOrderDate(String.valueOf(ppve.getDate()).replace("T", " "));
			dto.setWtQuantity(ppve.getTotalWtQuantity());
			data.add(dto);
		}
		return data;
	}
}
