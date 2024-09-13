package com.quickkoala.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.ViewPurchaseSummaryDto;
import com.quickkoala.entity.ViewPurchaseSummaryEntity;
import com.quickkoala.entity.ReceiveSummaryEntity;
import com.quickkoala.repository.ViewPurchaseSummaryRepository;
import com.quickkoala.repository.ReceiveSummaryViewRepository;

@Service
public class ViewPurchaseSummaryServiceImpl implements ViewPurchaseSummaryService{

	@Autowired
	private ViewPurchaseSummaryRepository purchaseProductViewRepository;
	
	@Override
	public List<ViewPurchaseSummaryDto> getAllOrders() {
		List<ViewPurchaseSummaryEntity> result = purchaseProductViewRepository.findByTotalWtQuantityGreaterThan(0);
		List<ViewPurchaseSummaryDto> data = new ArrayList<>();
		for(ViewPurchaseSummaryEntity ppve : result) {
			ViewPurchaseSummaryDto dto = new ViewPurchaseSummaryDto();
			dto.setOrderNumber(ppve.getOrderNumber());
			dto.setProductCode(ppve.getProductCode());
			dto.setName(ppve.getProductName());
			dto.setQuantity(String.format("%,d", ppve.getQuantity()));
			dto.setPrice(String.format("%,d", ppve.getPrice()));
			dto.setTotalPrice(String.format("%,d", ppve.getTotalPrice()));
			dto.setOrderDate(String.valueOf(ppve.getDate()).replace("T", " ").substring(0,19));
			dto.setWtQuantity(ppve.getTotalWtQuantity());
			data.add(dto);
		}
		return data;
	}
}
