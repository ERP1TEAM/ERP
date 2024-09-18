package com.quickkoala.service.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.supplier.SupplierEntity;
import com.quickkoala.repository.supplier.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService{
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	public List<SupplierEntity> getAllData() {
		return supplierRepository.findAllByOrderByCreatedDateDesc();
	}
	
	@Override
	public List<SupplierEntity> searchByName(String term) {
		return supplierRepository.findByNameContainingIgnoreCase(term);
	}
	
	@Override
	public SupplierEntity getCode(String name) {
		System.out.println(name);
		return supplierRepository.findByName(name);
	}
}
