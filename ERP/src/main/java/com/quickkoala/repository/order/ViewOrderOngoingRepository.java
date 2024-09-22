package com.quickkoala.repository.order;

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
	
	Page<ViewOrderOngoingEntity> findAll(Pageable pgab);
	Page<ViewOrderOngoingEntity> findByStatus(OrderStatus status, Pageable pgab);
	Page<ViewOrderOngoingEntity> findByNumberContainingOrderByNumberDesc(String param, Pageable pgab);
	Page<ViewOrderOngoingEntity> findBySalesNameContainingOrderByNumberDesc(String param, Pageable pageable);
	Page<ViewOrderOngoingEntity> findByManagerContainingOrderByNumberDesc(String param, Pageable pageable);
	
}