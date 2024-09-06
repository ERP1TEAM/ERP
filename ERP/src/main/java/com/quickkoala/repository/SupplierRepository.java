package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String>{
	List<SupplierEntity> findAllByOrderByCreatedDateDesc();
	List<SupplierEntity> findByNameContainingIgnoreCase(String term);
}
