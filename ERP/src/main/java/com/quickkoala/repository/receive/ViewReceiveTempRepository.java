package com.quickkoala.repository.receive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveTempEntity;

@Repository
public interface ViewReceiveTempRepository extends JpaRepository<ViewReceiveTempEntity, String> {
	Page<ViewReceiveTempEntity> findAllByOrderByCodeDesc(Pageable pageable);

	// 가입고코드로 검색
	Page<ViewReceiveTempEntity> findByCodeContainingOrderByCodeDesc(String orderNumber, Pageable pageable);

	// 발주번호으로 검색
	Page<ViewReceiveTempEntity> findByOrderNumberContainingOrderByCodeDesc(String productName, Pageable pageable);

	// 제조사로 검색
	Page<ViewReceiveTempEntity> findBySupplierNameContainingOrderByCodeDesc(String productName, Pageable pageable);

	// 상품명으로 검색
	Page<ViewReceiveTempEntity> findByProductNameContainingOrderByCodeDesc(String productName, Pageable pageable);

}
