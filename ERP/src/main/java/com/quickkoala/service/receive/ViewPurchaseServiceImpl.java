package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewPurchaseEntity;
import com.quickkoala.repository.receive.ViewPurchaseRepository;

@Service
public class ViewPurchaseServiceImpl implements ViewPurchaseService{
	
	@Autowired
	private ViewPurchaseRepository purchaseViewRepository;
	
	@Override
	public List<ViewPurchaseEntity> getAllData() {
		return purchaseViewRepository.findAll();
	}
	
	@Override
	public List<ViewPurchaseEntity> getSearchData(String code, String word) {
		List<ViewPurchaseEntity> result = null;
		if(code.equals("제조사")) {
			result = purchaseViewRepository.findByManufacturerContaining(word);
		}else if(code.equals("상품명")) {
			result = purchaseViewRepository.findByProductNameContaining(word);
		}
		return result;
	}
}
