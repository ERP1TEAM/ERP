package com.quickkoala.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveTempEntity;

@Repository
public interface ReceiveTempRepository extends JpaRepository<ReceiveTempEntity, String> {
	
}
