package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.PurchaseProductViewEntity;

@Repository
public interface PurchaseProductViewRepository extends JpaRepository<PurchaseProductViewEntity, String> {
	List<PurchaseProductViewEntity> findAllByOrderByOrderNumberDesc();
}
