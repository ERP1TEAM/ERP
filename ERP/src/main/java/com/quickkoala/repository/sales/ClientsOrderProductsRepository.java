package com.quickkoala.repository.sales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.sales.ClientsOrderProductsEntity;

public interface ClientsOrderProductsRepository extends JpaRepository<ClientsOrderProductsEntity, Long> {
	 // 특정 주문번호에 해당하는 상품들 가져오기
    List<ClientsOrderProductsEntity> findByClientsOrdersOrderId(String orderId);
    // 주문 번호와 상품 코드를 기준으로 상품 조회
    List<ClientsOrderProductsEntity> findByClientsOrdersOrderIdAndProductCode(String orderId, String productCode);
}
