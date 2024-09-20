package com.quickkoala.repository.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.sales.SalesEntity;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, String> {
    Optional<SalesEntity> findByCode(String code);
}