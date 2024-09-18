package com.quickkoala.repository.sales;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quickkoala.entity.sales.ClientsOrdersEntity;

public interface ClientsOrdersRepository extends JpaRepository<ClientsOrdersEntity, String> {
	// 이름, 연락처, 주문 날짜로 주문 검색
    Optional<ClientsOrdersEntity> findByNameAndTelAndOrderDate(
            String name, String tel, LocalDate orderDate
        );
        
    // 주문 날짜를 기준으로 해당 날짜에 존재하는 주문의 수를 세는 메서드
    Long countByOrderDate(LocalDate orderDate);
    
    // 주문번호로 검색
    Page<ClientsOrdersEntity> findByOrderIdContaining(String orderId, Pageable pageable);
    
    // 주문자명으로 검색
    Page<ClientsOrdersEntity> findByNameContaining(String name, Pageable pageable);
    
    // 주문번호와 날짜로 검색
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.orderId LIKE %:orderId% AND o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByOrderIdContainingAndOrderDate(
        @Param("orderId") String orderId, 
        @Param("orderDate") LocalDate orderDate, 
        Pageable pageable
    );

    // 주문자명과 날짜로 검색
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.name LIKE %:name% AND o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByNameContainingAndOrderDate(
        @Param("name") String name, 
        @Param("orderDate") LocalDate orderDate, 
        Pageable pageable
    );

    // 날짜로만 검색
    @Query("SELECT o FROM ClientsOrdersEntity o WHERE o.orderDate = :orderDate")
    Page<ClientsOrdersEntity> findByOrderDate(
        @Param("orderDate") LocalDate orderDate, 
        Pageable pageable
    );
}
