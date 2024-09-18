package com.quickkoala.repository.receive;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;

@Repository
public interface ViewReceiveSummaryRepository extends JpaRepository<ViewReceiveSummaryEntity, String>{
	List<ViewReceiveSummaryEntity> findAllByOrderByOrderNumberDesc();
	ViewReceiveSummaryEntity findByOrderNumber(String orderNumber);
	Page<ViewReceiveSummaryEntity> findAllByOrderByOrderNumberDesc(Pageable pageable);
	
	// 주문번호로 검색
	Page<ViewReceiveSummaryEntity> findByOrderNumberContainingOrderByOrderNumberDesc(String orderNumber, Pageable pageable);

    // 품목명으로 검색
    Page<ViewReceiveSummaryEntity> findByProductNameContainingOrderByOrderNumberDesc(String productName, Pageable pageable);
}
