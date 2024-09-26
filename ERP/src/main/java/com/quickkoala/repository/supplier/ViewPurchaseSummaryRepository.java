package com.quickkoala.repository.supplier;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;

@Repository
public interface ViewPurchaseSummaryRepository extends JpaRepository<ViewPurchaseSummaryEntity, String> {
	List<ViewPurchaseSummaryEntity> findAllByOrderByOrderNumberDesc();
	
	//value보다 큰 TotalWtQuantity인 데이터만 조회
	List<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(int value);
	
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanOrderByOrderNumberDesc(int totalWtQuantity, Pageable pageable);

	// 발주번호로 검색
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanAndOrderNumberContainingOrderByOrderNumberDesc(
	        int totalWtQuantity, String orderNumber, Pageable pageable);

	// 상품명으로 검색
	Page<ViewPurchaseSummaryEntity> findByTotalWtQuantityGreaterThanAndProductNameContainingOrderByOrderNumberDesc(
	        int totalWtQuantity, String productName, Pageable pageable);
	
	@Query("SELECT v FROM ViewPurchaseSummaryEntity v WHERE " + 
	        "(:searchField IS NULL OR " +
	        "(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + 
	        "(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + 
	        "(" +
	        "(:startDate IS NULL AND :endDate IS NOT NULL AND v.date <= :endDate) OR " +
	        "(:startDate IS NOT NULL AND :endDate IS NULL AND v.date >= :startDate) OR " +
	        "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND v.date BETWEEN :startDate AND :endDate) OR " +
	        "(:startDate IS NULL AND :endDate IS NULL)" +
	        ") AND " +
	        "v.totalWtQuantity > 0 " +
	        "ORDER BY v.orderNumber DESC")
	Page<ViewPurchaseSummaryEntity> search(
	        @Param("searchField") String searchField, 
	        @Param("codeType") String codeType, 
	        @Param("startDate") LocalDateTime startDate, 
	        @Param("endDate") LocalDateTime endDate, 
	        Pageable pageable);
}
