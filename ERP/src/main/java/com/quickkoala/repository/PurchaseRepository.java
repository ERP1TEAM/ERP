package com.quickkoala.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.PurchaseEntity;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {
	@Query("SELECT COUNT(p) FROM PurchaseEntity p WHERE DATE(p.orderDate) = :today")
    long countByOrderDate(@Param("today") LocalDate today);
	
	List<PurchaseEntity> findAllByOrderByOrderNumberDesc();
	List<PurchaseEntity> findAllByStatusOrderByOrderNumberDesc(String status);
}
