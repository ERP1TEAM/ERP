package com.quickkoala.repository.release;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;

@Repository
public interface ViewReleaseOngoingRepository extends JpaRepository<ViewReleaseOngoingEntity, String> {
    
    // 특정 상태 목록에서 모든 엔티티 조회
    Page<ViewReleaseOngoingEntity> findAllByStatusIn(ReleaseStatus[] statuses, Pageable pageable);
    
    // 번호로 필터링하면서 특정 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findByNumberContainingAndStatusIn(String number, ReleaseStatus[] statuses, Pageable pageable);
    
    // 주문 번호로 필터링하면서 특정 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findByOrderNumberContainingAndStatusIn(String orderNumber, ReleaseStatus[] statuses, Pageable pageable);
    
    // 판매자 이름으로 필터링하면서 특정 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findBySalesNameContainingAndStatusIn(String salesName, ReleaseStatus[] statuses, Pageable pageable);
    
    // 특정 날짜 범위에서 특정 상태 목록으로 조회
    Page<ViewReleaseOngoingEntity> findAllByStatusInAndDtBetween(ReleaseStatus[] statuses, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // 번호로 필터링하면서 특정 날짜 범위와 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findByStatusInAndNumberContainingAndDtBetween(ReleaseStatus[] statuses, String number, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // 주문 번호로 필터링하면서 특정 날짜 범위와 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findByStatusInAndOrderNumberContainingAndDtBetween(ReleaseStatus[] statuses, String orderNumber, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // 판매자 이름으로 필터링하면서 특정 날짜 범위와 상태 목록에서 조회
    Page<ViewReleaseOngoingEntity> findByStatusInAndSalesNameContainingAndDtBetween(ReleaseStatus[] statuses, String salesName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
