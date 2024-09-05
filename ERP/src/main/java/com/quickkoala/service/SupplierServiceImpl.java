package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.SupplierEntity;
import com.quickkoala.repository.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService{
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	public List<SupplierEntity> getAllData() {
		return supplierRepository.findAllByOrderByCreatedDateDesc();
	}
}
