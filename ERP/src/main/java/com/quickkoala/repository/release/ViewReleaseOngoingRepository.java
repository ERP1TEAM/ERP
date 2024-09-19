package com.quickkoala.repository.release;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;



@Repository
public interface ViewReleaseOngoingRepository extends JpaRepository<ViewReleaseOngoingEntity,String> {
	
	List<ViewReleaseOngoingEntity> findAllByStatusInOrderByNumberDesc (ReleaseStatus[] status);
	Page<ViewReleaseOngoingEntity> findByStatusIn(ReleaseStatus[] status, Pageable pageable);


	
}
