package com.quickkoala.repository.sales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.sales.ClientsOrderProductsEntity;

public interface ClientsOrderProductsRepository extends JpaRepository<ClientsOrderProductsEntity, Long> {
    List<ClientsOrderProductsEntity> findByClientsOrdersOrderId(String orderId);
}