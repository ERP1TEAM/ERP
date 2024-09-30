package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ViewProductStockSupplierEntity;

@Repository
public interface ViewProductStockSupplierRepository extends JpaRepository<ViewProductStockSupplierEntity, String> {
	
	List<ViewProductStockSupplierEntity> findAllByOrderByProductCodeDesc();
	
	Page<ViewProductStockSupplierEntity> findAllByOrderByProductCodeDesc(Pageable pageable);

	Page<ViewProductStockSupplierEntity> findByProductCodeContainingOrderByProductCodeDesc(String productCode, Pageable pageable);

	Page<ViewProductStockSupplierEntity> findByProductNameContainingOrderByProductCodeDesc(String productName, Pageable pageable);
	
	Page<ViewProductStockSupplierEntity> findBySupplierCodeContainingOrderByProductCodeDesc(String supplierCode, Pageable pageable);
	
	Page<ViewProductStockSupplierEntity> findBySupplierNameContainingOrderByProductCodeDesc(String supplierName, Pageable pageable);
	
	Page<ViewProductStockSupplierEntity> findByLocationCodeContainingOrderByProductCodeDesc(String locationCode, Pageable pageable);
	Page<ViewProductStockSupplierEntity> findByClassificationCodeContainingOrderByProductCodeDesc(String classificationCode, Pageable pageable);
	
	
}