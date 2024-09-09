package com.quickkoala.service;

import java.util.List;
import java.util.Optional;

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
	
	@Override
	public List<SupplierEntity> searchByName(String term) {
		return supplierRepository.findByNameContainingIgnoreCase(term);
	}
	
	@Override
    public Optional<SupplierEntity> findByCode(String code) {
        return supplierRepository.findByCode(code);
    }
}