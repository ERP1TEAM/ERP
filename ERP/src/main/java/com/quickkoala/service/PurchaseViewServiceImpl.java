package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.PurchaseViewEntity;
import com.quickkoala.repository.PurchaseViewRepository;

@Service
public class PurchaseViewServiceImpl implements PurchaseViewService{
	
	@Autowired
	private PurchaseViewRepository purchaseViewRepository;
	
	@Override
	public List<PurchaseViewEntity> getAllData() {
		return purchaseViewRepository.findAll();
	}
}
