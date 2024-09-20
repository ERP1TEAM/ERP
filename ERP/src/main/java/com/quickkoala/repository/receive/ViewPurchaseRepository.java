package com.quickkoala.repository.receive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewPurchaseEntity;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;

@Repository
public interface ViewPurchaseRepository extends JpaRepository<ViewPurchaseEntity, String> {
	List<ViewPurchaseEntity> findAll();
	List<ViewPurchaseEntity> findByManufacturerContaining(String manufacturer);
	List<ViewPurchaseEntity> findByProductNameContaining(String productName);
	
}
