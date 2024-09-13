package com.quickkoala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.ClientsOrderProductsEntity;

public interface ClientsOrderProductsRepository extends JpaRepository<ClientsOrderProductsEntity, Long> {
}
