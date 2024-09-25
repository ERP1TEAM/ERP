package com.quickkoala.repository.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.client.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {
	List<SupplierEntity> findAllByOrderByCreatedDateDesc();

	List<SupplierEntity> findByNameContainingIgnoreCase(String term);

	SupplierEntity findByName(String name);

	Page<SupplierEntity> findAllByOrderByCodeDesc(Pageable pageable);

	// 발주처코드로 검색
	Page<SupplierEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);

	// 발주처명으로 검색
	Page<SupplierEntity> findByNameContainingOrderByCodeDesc(String name, Pageable pageable);

	// 관리자 등록 - 코드로 관리자 소속사 확인
	Optional<SupplierEntity> findByCode(String code);
	
	@Query("SELECT MAX(s.code) FROM SupplierEntity s")
    String findMaxCode();
	
}
