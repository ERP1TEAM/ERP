package com.quickkoala.repository.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.dto.order.OrderOngoingDto;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;


@Repository
public interface ViewOrderOngoingRepository extends JpaRepository<ViewOrderOngoingEntity,String> {
	
	Page<ViewOrderOngoingEntity> findAllByStatusIn(OrderStatus[] status,Pageable pgab);
	Page<ViewOrderOngoingEntity> findAllByStatus(OrderStatus status, Pageable pgab);
	Page<ViewOrderOngoingEntity> findByNumberContainingAndStatusIn(String param, OrderStatus[] status, Pageable pgab);
	Page<ViewOrderOngoingEntity> findBySalesNameContainingAndStatusIn(String param, OrderStatus[] status, Pageable pageable);
	Page<ViewOrderOngoingEntity> findByManagerContainingAndStatusIn(String param, OrderStatus[] status, Pageable pageable);
	
	Page<ViewOrderOngoingEntity> findAllByDtBetweenAndStatusIn(LocalDateTime startDate, LocalDateTime endDate, OrderStatus[] status,Pageable pageable);
	Page<ViewOrderOngoingEntity> findByNumberContainingAndDtBetweenAndStatusIn(String param, LocalDateTime startDate, LocalDateTime endDate, OrderStatus[] status, Pageable pageable);
	Page<ViewOrderOngoingEntity> findBySalesNameContainingAndDtBetweenAndStatusIn(String param, LocalDateTime startDate, LocalDateTime endDate, OrderStatus[] status, Pageable pageable);
	Page<ViewOrderOngoingEntity> findByManagerContainingAndDtBetweenAndStatusIn(String param, LocalDateTime startDate, LocalDateTime endDate, OrderStatus[] status, Pageable pageable);
	Page<ViewOrderOngoingEntity> findAllByStatusAndDtBetween(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pgab);
}
