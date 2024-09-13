package com.quickkoala.repository;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.ClientsOrdersEntity;

public interface ClientsOrdersRepository extends JpaRepository<ClientsOrdersEntity, String> {
	// 이름, 연락처, 주문 날짜로 주문 검색
    Optional<ClientsOrdersEntity> findByNameAndTelAndOrderDate(
            String name, String tel, LocalDate orderDate
        );
        
    // 주문 날짜를 기준으로 해당 날짜에 존재하는 주문의 수를 세는 메서드
    Long countByOrderDate(LocalDate orderDate);
    
    
}
