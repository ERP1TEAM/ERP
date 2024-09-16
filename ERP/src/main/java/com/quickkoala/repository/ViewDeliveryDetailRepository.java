package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewDeliveryDetailEntity;

@Repository
public interface ViewDeliveryDetailRepository extends JpaRepository<ViewDeliveryDetailEntity, String>{
	List<ViewDeliveryDetailEntity> findAllByOrderByDeliveryCodeDesc();
	Page<ViewDeliveryDetailEntity> findAllByOrderByDeliveryCodeDesc(Pageable pageable);
}
