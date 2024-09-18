package com.quickkoala.repository.supplier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;

@Repository
public interface ViewDeliveryDetailRepository extends JpaRepository<ViewDeliveryDetailEntity, String> {
	Page<ViewDeliveryDetailEntity> findAllByOrderByDeliveryCodeDesc(Pageable pageable);

	// 납품번호로 검색
	Page<ViewDeliveryDetailEntity> findByDeliveryCodeContainingOrderByDeliveryCodeDesc(String deliveryCode,
			Pageable pageable);

	// 발주번호로 검색
	Page<ViewDeliveryDetailEntity> findByOrderNumberContainingOrderByDeliveryCodeDesc(String orderNumber,
			Pageable pageable);

	// 상품명으로 검색
	Page<ViewDeliveryDetailEntity> findByProductNameContainingOrderByDeliveryCodeDesc(String productName,
			Pageable pageable);

}
