package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.PurchaseDetailViewEntity;

@Repository
public interface PurchaseDetailViewRepository extends JpaRepository<PurchaseDetailViewEntity, String>{
	List<PurchaseDetailViewEntity> findAllByOrderByOrderNumberDesc();
}
