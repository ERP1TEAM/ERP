package com.quickkoala.repository.sales;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quickkoala.entity.sales.ClientsOrdersEntity;

public interface ClientsOrdersRepository extends JpaRepository<ClientsOrdersEntity, String> {

    // 이름, 연락처, 주문 날짜로 주문 검색
    Optional<ClientsOrdersEntity> findByNameAndTelAndOrderDate(String name, String tel, LocalDateTime orderDateTime);

    // 주문 날짜를 기준으로 해당 날짜에 존재하는 주문의 수를 세는 메서드
    Long countByOrderDate(LocalDateTime orderDate);

    // 주문번호로 검색 (회사 코드 포함)
    Page<ClientsOrdersEntity> findByCodeAndOrderIdContaining(String code, String orderId, Pageable pageable);

    // 주문자명으로 검색 (회사 코드 포함)
    Page<ClientsOrdersEntity> findByCodeAndNameContaining(String code, String name, Pageable pageable);

    // 회사 코드에 따라 주문 데이터를 조회
    Page<ClientsOrdersEntity> findByCode(String code, Pageable pageable);

    // 주문번호와 날짜로 검색 (회사 코드 포함)
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.code = :code AND o.orderId LIKE %:orderId% AND o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByCodeAndOrderIdContainingAndOrderDate(
        @Param("code") String code, 
        @Param("orderId") String orderId, 
        @Param("orderDate") LocalDateTime orderDate, 
        Pageable pageable
    );

    // 주문자명과 날짜로 검색 (회사 코드 포함)
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.code = :code AND o.name LIKE %:name% AND o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByCodeAndNameContainingAndOrderDate(
        @Param("code") String code,
        @Param("name") String name,
        @Param("orderDate") LocalDateTime orderDate,
        Pageable pageable
    );

    // 날짜로만 검색 (회사 코드 포함)
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.code = :code AND o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByCodeAndOrderDate(
        @Param("code") String code, 
        @Param("orderDate") LocalDateTime orderDate, 
        Pageable pageable
    );

    // 검색 필터를 적용한 주문 검색 쿼리 (회사 코드 필터 포함)
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.code = :code " +
           "AND (:searchText IS NULL OR o.orderId LIKE %:searchText% OR o.name LIKE %:searchText%) " +
           "AND (:searchDate IS NULL OR o.orderDate = :searchDate)")
    Page<ClientsOrdersEntity> searchOrders(
        @Param("code") String code,
        @Param("searchText") String searchText,
        @Param("searchDate") LocalDateTime searchDate,
        Pageable pageable);

    List<ClientsOrdersEntity> findByCode(String code);
}
