package com.quickkoala.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.OrderCancelEntity;

@Repository
public interface OrderCancelRepository extends JpaRepository<OrderCancelEntity,String> {

}
