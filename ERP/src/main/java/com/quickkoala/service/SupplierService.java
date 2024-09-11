package com.quickkoala.service;

import java.util.List;
import java.util.Optional;

import com.quickkoala.entity.SupplierEntity;

public interface SupplierService {
	List<SupplierEntity> getAllData();
	List<SupplierEntity> searchByName(String term);
	//관리자 등록 - 코드로 관리자 소속사 확인
	Optional<SupplierEntity> findByCode(String code);
}