package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.PurchaseDetailViewEntity;
import com.quickkoala.repository.PurchaseDetailViewRepository;

@Service
public class PurchaseDetailViewServiceImpl implements PurchaseDetailViewService{

	@Autowired
	private PurchaseDetailViewRepository purchaseDetailViewRepository;
	
	@Override
	public List<PurchaseDetailViewEntity> getData() {
		return purchaseDetailViewRepository.findAllByOrderByOrderNumberDesc();
	}
}
