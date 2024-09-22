package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.WarehouseEntity;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, String>{

	List<WarehouseEntity> findAllByOrderByCode();
	
	boolean existsByCode(String code);
	
	WarehouseEntity findByCode(String code);
	
	//창고 검색
	List<WarehouseEntity> findByNameContaining(String name);
	List<WarehouseEntity> findByCodeContaining(String code);
		
	
	Page<WarehouseEntity> findAllByOrderByCodeDesc(Pageable pageable);

	Page<WarehouseEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);
	Page<WarehouseEntity> findByNameContainingOrderByCodeDesc(String name, Pageable pageable);

}
