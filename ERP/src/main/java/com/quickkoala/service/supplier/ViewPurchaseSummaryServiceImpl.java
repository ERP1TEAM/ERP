package com.quickkoala.service.supplier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.ViewPurchaseSummaryDto;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;
import com.quickkoala.repository.receive.ViewReceiveSummaryRepository;
import com.quickkoala.repository.supplier.ViewPurchaseSummaryRepository;

@Service
public class ViewPurchaseSummaryServiceImpl implements ViewPurchaseSummaryService{

	@Autowired
	private ViewPurchaseSummaryRepository viewPurchaseSummaryRepository;
	
	/*
	@Override
	public List<ViewPurchaseSummaryDto> getAllOrders() {
		List<ViewPurchaseSummaryEntity> result = viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(0);
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
	*/
	
	@Override
	public Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(0, pageable);
	}
	@Override
	public Page<ViewPurchaseSummaryEntity> getPaginadtedData(int pno, int size, String code, String word) {
		Page<ViewPurchaseSummaryEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("발주번호")) {
			result = viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanAndOrderNumberContainingOrderByOrderNumberDesc(0, word, pageable);
		}else if(code.equals("상품명")) {
			result = viewPurchaseSummaryRepository.findByTotalWtQuantityGreaterThanAndProductNameContainingOrderByOrderNumberDesc(0, word, pageable);
		}
		return result;
	}
}
