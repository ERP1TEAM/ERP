package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {

	boolean existsByCode (String code);
	
	List<LocationEntity> findAllByOrderByCodeDesc();
}
