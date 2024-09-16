package com.quickkoala.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveReturnEntity;

@Repository
public interface ReceiveReturnRepository extends JpaRepository<ReceiveReturnEntity, String>{
	 @Query("SELECT COUNT(r) FROM ReceiveReturnEntity r WHERE r.date >= :startOfDay AND r.date < :endOfDay")
	 long countByDateRange(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
