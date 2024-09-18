package com.quickkoala.service.supplier;

import java.util.List;

import com.quickkoala.entity.supplier.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();
	List<SupplierEntity> searchByName(String term);
	SupplierEntity getCode(String name);
}
