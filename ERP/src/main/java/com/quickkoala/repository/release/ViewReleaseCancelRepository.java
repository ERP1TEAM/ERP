package com.quickkoala.repository.release;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.release.ViewReleaseCancelEntity;



@Repository
public interface ViewReleaseCancelRepository extends JpaRepository<ViewReleaseCancelEntity,String> {
	
	Page<ViewReleaseCancelEntity> findAll(Pageable pageable);
	Page<ViewReleaseCancelEntity> findByRelNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseCancelEntity> findByOrderNumberContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseCancelEntity> findByReasonContainingOrderByRelNumberDesc(String param, Pageable pageable);
	Page<ViewReleaseCancelEntity> findBySalesNameContainingOrderByRelNumberDesc(String param, Pageable pageable);

	

	
}
