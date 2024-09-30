package com.quickkoala.repository.supplier;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	
	@Query("SELECT v FROM ViewDeliveryReturnEntity v WHERE " + 
	        "(:searchField IS NULL OR " +
	        "(:codeType = '납품번호' AND LOWER(CONCAT(v.deliveryCode)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 납품번호
	        "(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 발주번호
	        "(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + // 상품명
	        "(:supplierCode IS NULL OR v.supplierCode = :supplierCode) AND " + // supplierCode 조건 추가
	        "(" +
	        "(:startDate IS NULL AND :endDate IS NOT NULL AND v.returnDate <= :endDate) OR " +
	        "(:startDate IS NOT NULL AND :endDate IS NULL AND v.returnDate >= :startDate) OR " +
	        "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND v.returnDate BETWEEN :startDate AND :endDate) OR " +
	        "(:startDate IS NULL AND :endDate IS NULL)" +
	        ") " +
	        "ORDER BY v.deliveryCode DESC") // 납품번호를 기준으로 내림차순 정렬
	Page<ViewDeliveryReturnEntity> search(
	        @Param("searchField") String searchField, // 검색할 값
	        @Param("codeType") String codeType, // 코드 유형 (납품번호, 발주번호 등)
	        @Param("supplierCode") String supplierCode, // supplierCode 파라미터 추가
	        @Param("startDate") LocalDateTime startDate, 
	        @Param("endDate") LocalDateTime endDate, 
	        Pageable pageable);

}
