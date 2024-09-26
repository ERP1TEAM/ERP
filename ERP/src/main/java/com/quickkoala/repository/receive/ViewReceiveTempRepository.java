package com.quickkoala.repository.receive;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query("SELECT v FROM ViewReceiveTempEntity v WHERE " + "(:searchField IS NULL OR "
			+ "(:codeType = '가입고코드' AND LOWER(CONCAT(v.code)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + 
			"(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + 
			"(:codeType = '제조사' AND LOWER(CONCAT(v.supplierName)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + 
			"(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + 
			"(:startDate IS NULL OR " + 
			"(v.date >= :startDate AND v.date <= :endDate)) " + 
			"ORDER BY v.code DESC") // 
	Page<ViewReceiveTempEntity> search(@Param("searchField") String searchField, 
			@Param("codeType") String codeType,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

}
