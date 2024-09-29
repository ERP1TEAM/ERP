package com.quickkoala.repository.release;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;

@Repository
public interface ViewReleaseCompleteRepository extends JpaRepository<ViewReleaseCompleteEntity, String> {
	
	Page<ViewReleaseCompleteEntity> findAll( Pageable pageable);
    
    // relNumber로 필터링
    Page<ViewReleaseCompleteEntity> findByRelNumberContaining(String relNumber, Pageable pageable);
    
    // orderNumber로 필터링
    Page<ViewReleaseCompleteEntity> findByOrderNumberContaining(String orderNumber, Pageable pageable);
    
    // salesName으로 필터링
    Page<ViewReleaseCompleteEntity> findBySalesNameContaining(String salesName, Pageable pageable);
    
    // 특정 날짜 범위에서 조회
    Page<ViewReleaseCompleteEntity> findAllByDtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // relNumber로 필터링하면서 특정 날짜 범위에서 조회
    Page<ViewReleaseCompleteEntity> findByRelNumberContainingAndDtBetween(String relNumber, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // orderNumber로 필터링하면서 특정 날짜 범위에서 조회
    Page<ViewReleaseCompleteEntity> findByOrderNumberContainingAndDtBetween(String orderNumber, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // salesName으로 필터링하면서 특정 날짜 범위에서 조회
    Page<ViewReleaseCompleteEntity> findBySalesNameContainingAndDtBetween(String salesName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
