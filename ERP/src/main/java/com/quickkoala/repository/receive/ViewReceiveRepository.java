package com.quickkoala.repository.receive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewReceiveEntity;

@Repository
public interface ViewReceiveRepository extends JpaRepository<ViewReceiveEntity, String> {
	List<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc();

	long count();

	Page<ViewReceiveEntity> findAllByOrderByReceiveCodeDesc(Pageable pageable);

	// 입고번호로 검색
	Page<ViewReceiveEntity> findByReceiveCodeContainingOrderByReceiveCodeDesc(String orderNumber, Pageable pageable);

	// 발주번호으로 검색
	Page<ViewReceiveEntity> findByOrderNumberContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);

	// 제조사로 검색
	Page<ViewReceiveEntity> findBySupplierNameContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);

	// 상품명으로 검색
	Page<ViewReceiveEntity> findByProductNameContainingOrderByReceiveCodeDesc(String productName, Pageable pageable);

	@Query("SELECT v FROM ViewReceiveEntity v WHERE " + "(:searchField IS NULL OR "
			+ "(:codeType = '입고번호' AND LOWER(CONCAT(v.receiveCode)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 입고번호
			"(:codeType = '발주번호' AND LOWER(CONCAT(v.orderNumber)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 발주번호
			"(:codeType = '제조사' AND LOWER(CONCAT(v.supplierName)) LIKE LOWER(CONCAT('%', :searchField, '%'))) OR " + // 제조사
			"(:codeType = '상품명' AND LOWER(CONCAT(v.productName)) LIKE LOWER(CONCAT('%', :searchField, '%')))) AND " + // 상품명
			"(:startDate IS NULL OR " + // 날짜가 없을 경우
			"(v.receiveDate >= :startDate AND v.receiveDate <= :endDate)) " + // 날짜가 있을 경우
			"ORDER BY v.receiveCode DESC") // receiveCode를 기준으로 내림차순 정렬
	Page<ViewReceiveEntity> search(@Param("searchField") String searchField, // 검색할 값
			@Param("codeType") String codeType, // 어떤 코드인지 (입고번호, 발주번호 등)
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
