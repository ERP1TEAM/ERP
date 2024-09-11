package com.quickkoala.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String>{
	List<SupplierEntity> findAllByOrderByCreatedDateDesc();
	List<SupplierEntity> findByNameContainingIgnoreCase(String term);
	//관리자 등록 - 코드로 관리자 소속사 확인
	Optional<SupplierEntity> findByCode(String code);
}