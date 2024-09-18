package com.quickkoala.repository.receive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;
import com.quickkoala.entity.receive.ViewReceiveEntity;

@Repository
public interface ViewPurchaseDetailRepository extends JpaRepository<ViewPurchaseDetailEntity, String> {
	Page<ViewPurchaseDetailEntity> findAllByOrderByOrderNumberDesc(Pageable pageable);

	Page<ViewPurchaseDetailEntity> findByStatusContainingOrderByOrderNumberDesc(String status, Pageable pageable);

	// 발주번호으로 검색
	Page<ViewPurchaseDetailEntity> findByOrderNumberContainingOrderByOrderNumberDesc(String productName, Pageable pageable);

	// 제조사로 검색
	Page<ViewPurchaseDetailEntity> findBySupplierNameContainingOrderByOrderNumberDesc(String productName, Pageable pageable);

	// 상품명으로 검색
	Page<ViewPurchaseDetailEntity> findByProductNameContainingOrderByOrderNumberDesc(String productName, Pageable pageable);
}
