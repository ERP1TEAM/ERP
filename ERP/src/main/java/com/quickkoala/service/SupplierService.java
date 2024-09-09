package com.quickkoala.service;

import java.util.List;
import java.util.Optional;

import com.quickkoala.entity.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();
	List<SupplierEntity> searchByName(String term);
	Optional<SupplierEntity> findByCode(String code);
}