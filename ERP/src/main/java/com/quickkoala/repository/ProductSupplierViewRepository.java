package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ProductSupplierViewEntity;
import com.quickkoala.entity.PurchaseProductViewEntity;

@Repository
public interface ProductSupplierViewRepository extends JpaRepository<ProductSupplierViewEntity, String> {
	List<ProductSupplierViewEntity> findAll();
}
