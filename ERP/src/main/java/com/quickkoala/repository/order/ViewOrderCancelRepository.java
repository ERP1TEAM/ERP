package com.quickkoala.repository.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.quickkoala.entity.order.ViewOrderCancelEntity;

@Repository
public interface ViewOrderCancelRepository extends JpaRepository<ViewOrderCancelEntity,String> {
	
	List<ViewOrderCancelEntity> findAllByOrderByOrderNumberDesc();
	
	Page<ViewOrderCancelEntity> findAll(Pageable pageable);
	Page<ViewOrderCancelEntity> findByOrderNumberContaining(String param, Pageable pageable);
	Page<ViewOrderCancelEntity> findBySalesNameContaining(String param, Pageable pageable);
	Page<ViewOrderCancelEntity> findByManagerContaining(String param, Pageable pageable);
	
	Page<ViewOrderCancelEntity> findAllByDtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findByOrderNumberContainingAndDtBetween(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findBySalesNameContainingAndDtBetween(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	Page<ViewOrderCancelEntity> findByManagerContainingAndDtBetween(String param, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
}