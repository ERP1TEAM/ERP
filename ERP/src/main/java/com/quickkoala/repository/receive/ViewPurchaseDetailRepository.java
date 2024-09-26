package com.quickkoala.repository.receive;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;

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
	
	@Query("SELECT v FROM ViewPurchaseDetailEntity v WHERE " 
	        + "(:searchField IS NULL OR " 
	        + "(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR "
	        + "(:codeType = '제조사' AND LOWER(CONCAT(v.supplierName)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " 
	        + "(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " 
	        + "("
	        + "(:startDate IS NULL AND :endDate IS NOT NULL AND v.orderDate <= :endDate) OR " // startDate가 NULL이고 endDate는 NULL이 아닐 때
	        + "(:startDate IS NOT NULL AND :endDate IS NULL AND v.orderDate >= :startDate) OR " // startDate는 NULL이 아니고 endDate는 NULL일 때
	        + "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND v.orderDate BETWEEN :startDate AND :endDate) OR " // 둘 다 NULL이 아닐 때
	        + "(:startDate IS NULL AND :endDate IS NULL)" // 둘 다 NULL일 때
	        + ") " 
	        + "ORDER BY v.orderNumber DESC")
	Page<ViewPurchaseDetailEntity> search(
	        @Param("searchField") String searchField, // 검색할 값
	        @Param("codeType") String codeType, // 어떤 코드인지 (입고번호, 발주번호 등)
	        @Param("startDate") LocalDateTime startDate, 
	        @Param("endDate") LocalDateTime endDate, 
	        Pageable pageable);

}
