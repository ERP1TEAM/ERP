package com.quickkoala.service.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.client.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();

	List<SupplierEntity> searchByName(String term);

	SupplierEntity getCode(String name);

	Page<SupplierEntity> getPaginatedData(int pno, int size);

	Page<SupplierEntity> getPaginatedData(int pno, int size, String code, String word);

	// 관리자 등록 - 코드로 관리자 소속사 확인
	Optional<SupplierEntity> findByCode(String code);
	
	SupplierEntity addSupplier(SupplierEntity supplierEntity);
	
}
