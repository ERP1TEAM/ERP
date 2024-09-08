package com.quickkoala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.WarehouseEntity;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, String>{

	List<WarehouseEntity> findAllByOrderByCodeDesc();
}
