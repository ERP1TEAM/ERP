package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ProductSupplierViewEntity;
import com.quickkoala.repository.ProductSupplierViewRepository;

@Service
public class ProductSupplierViewServiceImpl implements ProductSupplierViewService{
	
	@Autowired
	private ProductSupplierViewRepository productSupplierViewRepository;
	
	@Override
	public List<ProductSupplierViewEntity> getAllData() {
		return productSupplierViewRepository.findAll();
	}
}
