package com.quickkoala.repository.receive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.receive.ViewLocationProductEntity;

@Repository
public interface ViewLocationProductRepository extends JpaRepository<ViewLocationProductEntity, String>{
	List<ViewLocationProductEntity> findAllByOrderByLocationCode();
	int countByProductCode(String productCode);
	ViewLocationProductEntity findLocationCodeByProductCode(String productCode);
}
