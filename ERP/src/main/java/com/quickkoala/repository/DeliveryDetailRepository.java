package com.quickkoala.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.DeliveryDetailEntity;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetailEntity, String> {
	@Query("SELECT COUNT(d) FROM DeliveryDetailEntity d WHERE DATE(d.date) = :today")
    long countByDate(@Param("today") LocalDate today);
	
	List<DeliveryDetailEntity> findAllByOrderByCodeDesc();
	
	DeliveryDetailEntity findByOrderNumber(String orderNumber);
}
