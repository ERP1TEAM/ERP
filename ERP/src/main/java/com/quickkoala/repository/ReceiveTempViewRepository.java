package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ReceiveTempViewEntity;

@Repository
public interface ReceiveTempViewRepository extends JpaRepository<ReceiveTempViewEntity, String> {
	List<ReceiveTempViewEntity> findAllByOrderByCodeDesc();
	
	// wtQuantity가 0인 엔티티를 code 기준으로 내림차순 정렬하여 가져오는 메서드
    List<ReceiveTempViewEntity> findByWtQuantityOrderByCodeDesc(int wtQuantity);
    
 // wtQuantity가 0이 아닌 엔티티를 code 기준으로 내림차순 정렬하여 가져오는 메서드
    List<ReceiveTempViewEntity> findByWtQuantityNotOrderByCodeDesc(int wtQuantity);
}
