package com.quickkoala.repository.supplier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;

@Repository
public interface ViewPurchaseSummaryRepository extends JpaRepository<ViewPurchaseSummaryEntity, String> {
	List<ViewPurchaseSummaryEntity> findAllByOrderByOrderNumberDesc();
	
	//value보다 큰 TotalWtQuantity인 데이터만 조회
	List<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(int value);
	
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(int totalWtQuantity, Pageable pageable);

	// 발주번호로 검색
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanAndOrderNumberContainingOrderByOrderNumberDesc(
	        int totalWtQuantity, String orderNumber, Pageable pageable);

	// 상품명으로 검색
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanAndProductNameContainingOrderByOrderNumberDesc(
	        int totalWtQuantity, String productName, Pageable pageable);
}
