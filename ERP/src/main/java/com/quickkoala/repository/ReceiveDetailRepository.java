package com.quickkoala.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveDetailEntity;

@Repository
public interface ReceiveDetailRepository extends JpaRepository<ReceiveDetailEntity, String>{
	@Query("SELECT COUNT(p) FROM ReceiveDetailEntity p WHERE DATE(p.date) = :today")
    long countByCode(@Param("today") LocalDate today);
	
}
