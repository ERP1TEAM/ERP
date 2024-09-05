package com.quickkoala.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveTempEntity;

@Repository
public interface ReceiveTempRepository extends JpaRepository<ReceiveTempEntity, String> {
	@Query("SELECT COUNT(p) FROM ReceiveTempEntity p WHERE DATE(p.date) = :today")
    long countByOrderDate(@Param("today") LocalDate today);
	
	List<ReceiveTempEntity> findAllByOrderByCodeDesc();
	ReceiveTempEntity findByCode(String data);
	
}
