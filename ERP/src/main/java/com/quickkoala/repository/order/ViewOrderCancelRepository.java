package com.quickkoala.repository.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.dto.order.OrderCancelDto;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;


@Repository
public interface ViewOrderCancelRepository extends JpaRepository<ViewOrderCancelEntity,String> {
	
	List<ViewOrderCancelEntity> findAllByOrderByOrderNumberDesc();
	
	Page<ViewOrderCancelEntity> findAll(Pageable pageable);


	
}
