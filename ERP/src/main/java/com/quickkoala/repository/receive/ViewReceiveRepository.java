package com.quickkoala.repository.receive;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;

@Repository
public interface ViewReceiveRepository extends JpaRepository<ViewReceiveEntity, String> {
	List<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc();

	long count();

	Page<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc(Pageable pageable);

	// 입고번호로 검색
	Page<ViewReceiveEntity> findByReceiveCodeContainingOrderByReceiveCodeDesc(String orderNumber, Pageable pageable);

	// 발주번호으로 검색
	Page<ViewReceiveEntity> findByOrderNumberContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);

	// 제조사로 검색
	Page<ViewReceiveEntity> findBySupplierNameContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);

	// 상품명으로 검색
	Page<ViewReceiveEntity> findByProductNameContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);
}
