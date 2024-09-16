package com.quickkoala.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.ViewDeliveryReturnEntity;

@Repository
public interface ViewDeliveryReturnRepository extends JpaRepository<ViewDeliveryReturnEntity, String>{
	Page<ViewDeliveryReturnEntity> findAllByOrderByReturnDateDesc(Pageable pageable);
}
