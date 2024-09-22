package com.quickkoala.repository.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.OrderReleaseEntity2;

@Repository
public interface OrderReleaseRepository2 extends JpaRepository<OrderReleaseEntity2,String> {
	
	List<OrderReleaseEntity2> findAllByOrderByNumberDesc();
	
	@Query("SELECT DATE_FORMAT(NOW(), '%Y%m%d')")
	public String mysql_now();
	
	@Query("SELECT now()")
	public LocalDateTime mysql_now2();
	
	public List<OrderReleaseEntity2> findByNumberLikeOrderByNumberDesc(String day);
	
	@Transactional
	@Modifying
	@Query("update OrderReleaseEntity set status =:status where number = :id")
	public int updateStatus(@Param("id") String id,  @Param("status") OrderReleaseEntity2.ReleaseStatus status);
	
	
}
