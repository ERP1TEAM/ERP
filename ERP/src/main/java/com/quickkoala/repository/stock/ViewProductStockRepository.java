package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ViewProductStockEntity;

@Repository
public interface ViewProductStockRepository extends JpaRepository<ViewProductStockEntity, String> {
	
	List<ViewProductStockEntity> findAllByOrderByProductCodeDesc();
	
	Page<ViewProductStockEntity> findAllByOrderByProductCodeDesc(Pageable pageable);

	Page<ViewProductStockEntity> findByProductCodeContainingOrderByProductCodeDesc(String productCode, Pageable pageable);

	Page<ViewProductStockEntity> findByProductNameContainingOrderByProductCodeDesc(String productName, Pageable pageable);
	
	Page<ViewProductStockEntity> findBySupplierCodeContainingOrderByProductCodeDesc(String supplierCode, Pageable pageable);
	
	Page<ViewProductStockEntity> findByLocationCodeContainingOrderByProductCodeDesc(String locationCode, Pageable pageable);
	
	Page<ViewProductStockEntity> findByClassificationCodeOrderByProductCodeDesc(String classificationCode, Pageable pageable);
}
