package com.quickkoala.repository.supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;
import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;

@Repository
public interface ViewDeliveryReturnRepository extends JpaRepository<ViewDeliveryReturnEntity, String> {
	Page<ViewDeliveryReturnEntity> findAllByOrderByReturnDateDesc(Pageable pageable);

	// 납품번호로 검색
	Page<ViewDeliveryReturnEntity> findByDeliveryCodeContainingOrderByReturnDateDesc(String deliveryCode,
			Pageable pageable);

	// 발주번호로 검색
	Page<ViewDeliveryReturnEntity> findByOrderNumberContainingOrderByReturnDateDesc(String orderNumber,
			Pageable pageable);

	// 상품명으로 검색
	Page<ViewDeliveryReturnEntity> findByProductNameContainingOrderByReturnDateDesc(String productName,
			Pageable pageable);
}
