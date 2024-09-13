package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewPurchaseSummaryEntity;

@Repository
public interface ViewPurchaseSummaryRepository extends JpaRepository<ViewPurchaseSummaryEntity, String> {
	List<ViewPurchaseSummaryEntity> findAllByOrderByOrderNumberDesc();
	
	//value보다 큰 TotalWtQuantity인 데이터만 조회
	List<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThan(int value);
}
