package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();
	List<SupplierEntity> searchByName(String term);
}
