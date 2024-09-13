package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.PurchaseViewEntity;
import com.quickkoala.entity.ViewPurchaseSummaryEntity;

@Repository
public interface PurchaseViewRepository extends JpaRepository<PurchaseViewEntity, String> {
	List<PurchaseViewEntity> findAll();
}
