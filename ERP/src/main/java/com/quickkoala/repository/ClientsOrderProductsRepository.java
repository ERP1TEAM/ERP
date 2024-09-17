package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.ClientsOrderProductsEntity;

public interface ClientsOrderProductsRepository extends JpaRepository<ClientsOrderProductsEntity, Long> {
	 // 특정 주문번호에 해당하는 상품들 가져오기
    List<ClientsOrderProductsEntity> findByClientsOrdersOrderId(String orderId);
}
