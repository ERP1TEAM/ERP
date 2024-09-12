package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.WarehouseEntity;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, String>{

	List<WarehouseEntity> findAllByOrderByCodeDesc();
	
	boolean existsByCode(String code);
	
	WarehouseEntity findByCode(String code);
}
