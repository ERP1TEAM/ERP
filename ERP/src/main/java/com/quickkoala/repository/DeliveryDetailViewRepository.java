package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.DeliveryDetailViewEntity;

@Repository
public interface DeliveryDetailViewRepository extends JpaRepository<DeliveryDetailViewEntity, String>{
	List<DeliveryDetailViewEntity> findAllByOrderByDeliveryCode();
}
