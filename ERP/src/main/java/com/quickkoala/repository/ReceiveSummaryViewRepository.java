package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveSummaryEntity;

@Repository
public interface ReceiveSummaryViewRepository extends JpaRepository<ReceiveSummaryEntity, String>{
	List<ReceiveSummaryEntity> findAllByOrderByOrderNumberDesc();
	ReceiveSummaryEntity findByOrderNumber(String orderNumber);
}
