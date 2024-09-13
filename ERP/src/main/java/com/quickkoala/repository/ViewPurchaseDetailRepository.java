package com.quickkoala.repository;

import java.util.List; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewPurchaseDetailEntity;

@Repository
public interface ViewPurchaseDetailRepository extends JpaRepository<ViewPurchaseDetailEntity, String>{
	List<ViewPurchaseDetailEntity> findAllByOrderByOrderNumberDesc();
	
	List<ViewPurchaseDetailEntity> findByStatusContainingOrderByOrderNumberDesc(String status);
	
	Page<ViewPurchaseDetailEntity> findAllByOrderByOrderNumberDesc(Pageable pageable);
	
	Page<ViewPurchaseDetailEntity> findAll(Pageable pageable);
	
	Page<ViewPurchaseDetailEntity> findByStatusContainingOrderByOrderNumberDesc(String status, Pageable pageable);
}
