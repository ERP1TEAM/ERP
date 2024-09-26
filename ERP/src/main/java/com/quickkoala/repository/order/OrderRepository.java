package com.quickkoala.repository.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;

import jakarta.transaction.Transactional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,String> {
	
	@Query("SELECT o.number FROM OrderEntity o WHERE o.number LIKE CONCAT(:date, '%') ORDER BY o.number DESC")
	List<String> findMaxOrderNumber(@Param("date") String date);
	
	Long countByDt(LocalDateTime date);
	
	List<OrderEntity> findByOrderId(String orderId);
	
	@Modifying
	@Transactional
	@Query("UPDATE OrderEntity o SET o.status = :status WHERE o.number = :number")
	int updateStatus(@Param(value="status") OrderStatus status,@Param(value="number") String number);
	
	@Modifying
	@Transactional
	@Query("UPDATE OrderEntity o SET o.status = :status WHERE o.number = :ids")
	int updateStatusMultipleIds(@Param("status") OrderStatus status,@Param("ids") List<String> ids);
	

    // JPQL을 사용하여 orderId로 상태만 조회
    @Query("SELECT o.status FROM OrderEntity o WHERE o.orderId = :orderId")
    Optional<OrderStatus> getStatusByOrderId(@Param("orderId") String orderId);
	
}
