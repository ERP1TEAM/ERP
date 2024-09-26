package com.quickkoala.repository.receive;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	
	@Query("SELECT v FROM ViewReceiveReturnEntity v WHERE " + "(:searchField IS NULL OR "
			+ "(:codeType = '반품번호' AND LOWER(CONCAT(v.returnNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 입고번호
			"(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 발주번호
			"(:codeType = '제조사' AND LOWER(CONCAT(v.supplierName)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 제조사
			"(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + // 상품명
			"("
	        + "(:startDate IS NULL AND :endDate IS NOT NULL AND v.returnDate <= :endDate) OR "
	        + "(:startDate IS NOT NULL AND :endDate IS NULL AND v.returnDate >= :startDate) OR "
	        + "(:startDate IS NOT NULL AND :endDate IS NOT NULL AND v.returnDate BETWEEN :startDate AND :endDate) OR "
	        + "(:startDate IS NULL AND :endDate IS NULL)"
	        + ") "
			+ "ORDER BY v.returnNumber DESC") // receiveCode를 기준으로 내림차순 정렬
	Page<ViewReceiveReturnEntity> search(@Param("searchField") String searchField, // 검색할 값
			@Param("codeType") String codeType, // 어떤 코드인지 (입고번호, 발주번호 등)
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
