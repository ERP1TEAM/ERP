package com.quickkoala.repository.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderCancelEntity;

@Repository
public interface ViewOrderCancelRepository extends JpaRepository<ViewOrderCancelEntity,String> {
	
	List<ViewOrderCancelEntity> findAllByOrderByOrderNumberDesc();
	
	Page<ViewOrderCancelEntity> findAll(Pageable pageable);
	Page<ViewOrderCancelEntity> findByOrderNumberContainingOrderByOrderNumberDesc(String param, Pageable pageable);
	Page<ViewOrderCancelEntity> findBySalesNameContainingOrderByOrderNumberDesc(String param, Pageable pageable);
	Page<ViewOrderCancelEntity> findByManagerContainingOrderByOrderNumberDesc(String param, Pageable pageable);
	
	Page<ViewOrderCancelEntity> findAllByDtBetween(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findByOrderNumberContainingAndDtBetweenOrderByOrderNumberDesc(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findBySalesNameContainingAndDtBetweenOrderByOrderNumberDesc(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findByManagerContainingAndDtBetweenOrderByOrderNumberDesc(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
}