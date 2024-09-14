package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewReceiveTempEntity;

@Repository
public interface ViewReceiveTempRepository extends JpaRepository<ViewReceiveTempEntity, String> {
	List<ViewReceiveTempEntity> findAllByOrderByCodeDesc();
	
	// wtQuantity가 0인 엔티티를 code 기준으로 내림차순 정렬하여 가져오는 메서드
    List<ViewReceiveTempEntity> findByWtQuantityOrderByCodeDesc(int wtQuantity);
    
    // wtQuantity가 0이 아닌 엔티티를 code 기준으로 내림차순 정렬하여 가져오는 메서드
    List<ViewReceiveTempEntity> findByWtQuantityNotOrderByCodeDesc(int wtQuantity);
    
    Page<ViewReceiveTempEntity> findAllByOrderByCodeDesc(Pageable pageable);
}
