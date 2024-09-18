package com.quickkoala.repository.receive;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;

@Repository
public interface ViewReceiveReturnRepository extends JpaRepository<ViewReceiveReturnEntity, String> {
	List<ViewReceiveReturnEntity> findAllByOrderByReturnDateDesc();

	Page<ViewReceiveReturnEntity> findAllByOrderByReturnDateDesc(Pageable pageable);

	// 반품번호로 검색
	Page<ViewReceiveReturnEntity> findByReturnNumberContainingOrderByReturnNumberDesc(String orderNumber, Pageable pageable);

	// 발주번호으로 검색
	Page<ViewReceiveReturnEntity> findByOrderNumberContainingOrderByReturnNumberDesc(String productName, Pageable pageable);

	// 제조사로 검색
	Page<ViewReceiveReturnEntity> findBySupplierNameContainingOrderByReturnNumberDesc(String productName, Pageable pageable);

	// 상품명으로 검색
	Page<ViewReceiveReturnEntity> findByProductNameContainingOrderByReturnNumberDesc(String productName, Pageable pageable);
}
