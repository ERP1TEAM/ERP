package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewReceiveSummaryEntity;

@Repository
public interface ViewReceiveSummaryRepository extends JpaRepository<ViewReceiveSummaryEntity, String>{
	List<ViewReceiveSummaryEntity> findAllByOrderByOrderNumberDesc();
	ViewReceiveSummaryEntity findByOrderNumber(String orderNumber);
	Page<ViewReceiveSummaryEntity> findAllByOrderByOrderNumberDesc(Pageable pageable);
}
