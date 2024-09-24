package com.quickkoala.repository.release;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.OrderReleaseEntity;

@Repository
public interface OrderReleaseRepository extends JpaRepository<OrderReleaseEntity,String> {
	
	List<OrderReleaseEntity> findAllByOrderByNumberDesc();
	
	@Query("SELECT DATE_FORMAT(NOW(), '%Y%m%d')")
	public String mysql_now();
	
	@Query("SELECT now()")
	public LocalDateTime mysql_now2();
	
	public List<OrderReleaseEntity> findByNumberLikeOrderByNumberDesc(String day);
	
	@Transactional
	@Modifying
	@Query("update OrderReleaseEntity set status =:status where number = :id")
	public int updateStatus(@Param("id") String id,  @Param("status") OrderReleaseEntity.ReleaseStatus status);
	
	@Transactional
	@Modifying
	@Query("delete from OrderReleaseEntity where number = :id")
	public int deleteByNumber(@Param("id") String id);
	
	
}
