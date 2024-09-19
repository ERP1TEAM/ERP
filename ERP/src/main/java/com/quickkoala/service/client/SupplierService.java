package com.quickkoala.service.client;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.client.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();

	List<SupplierEntity> searchByName(String term);

	SupplierEntity getCode(String name);

	Page<SupplierEntity> getPaginatedData(int pno, int size);

	Page<SupplierEntity> getPaginatedData(int pno, int size, String code, String word);
}
