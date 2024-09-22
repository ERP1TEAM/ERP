package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;



@Repository
public interface ViewReleaseOngoingRepository extends JpaRepository<ViewReleaseOngoingEntity,String> {
	
	List<ViewReleaseOngoingEntity> findAllByStatusInOrderByNumberDesc (ReleaseStatus[] status);
	Page<ViewReleaseOngoingEntity> findByStatus(ReleaseStatus status, Pageable pageable);
	
	Page<ViewReleaseOngoingEntity> findByNumberContainingOrderByNumberDesc(String param, Pageable pgab);
	Page<ViewReleaseOngoingEntity> findByOrderNumberContainingOrderByNumberDesc(String param,  Pageable pgab);
	Page<ViewReleaseOngoingEntity> findBySalesNameContainingOrderByNumberDesc(String param, Pageable pageable);
	

	
}
