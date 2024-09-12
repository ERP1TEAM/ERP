package com.quickkoala.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.LotEntity;

@Repository
public interface LotRepository extends JpaRepository<LotEntity, String>{
	@Query("SELECT COUNT(l) FROM LotEntity l WHERE DATE(l.receiveDate) = :today")
	long countByOrderDate(@Param("today") LocalDate today);
	
	@Query("SELECT MAX(SUBSTRING(l.lotNumber, LENGTH(l.lotNumber) - 2)) FROM LotEntity l WHERE l.productCode = :productCode AND DATE(l.receiveDate) = :receiveDate")
    Optional<Integer> findMaxSerialNumberByProductCodeAndDate(@Param("productCode") String productCode, @Param("receiveDate") LocalDate receiveDate);
}
