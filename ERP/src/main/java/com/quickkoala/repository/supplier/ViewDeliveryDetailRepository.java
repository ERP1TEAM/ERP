package com.quickkoala.repository.supplier;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	
	@Query("SELECT v FROM ViewDeliveryDetailEntity v WHERE " + "(:searchField IS NULL OR "
			+ "(:codeType = '납품번호' AND LOWER(CONCAT(v.deliveryCode)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 입고번호
			"(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 발주번호
			"(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + // 상품명
			"(:startDate IS NULL OR " + // 날짜가 없을 경우
			"(v.date >= :startDate AND v.date <= :endDate)) " + // 날짜가 있을 경우
			"ORDER BY v.deliveryCode DESC") // receiveCode를 기준으로 내림차순 정렬
	Page<ViewDeliveryDetailEntity> search(@Param("searchField") String searchField, // 검색할 값
			@Param("codeType") String codeType, // 어떤 코드인지 (입고번호, 발주번호 등)
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

}
