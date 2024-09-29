package com.quickkoala.repository.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {

	boolean existsByCode (String code);
	
	List<LocationEntity> findAllByOrderByCodeAsc();
	
	List<LocationEntity> findAllByOrderByCodeDesc();
	
	Page<LocationEntity> findAllByOrderByCodeDesc(Pageable pageable);

	Page<LocationEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);

	Page<LocationEntity> findByWarehouseCodeContainingOrderByCodeDesc(String warehouseCode, Pageable pageable);
	
	Page<LocationEntity> findByRackCodeContainingOrderByCodeDesc(String rackCode, Pageable pageable);
	
	Page<LocationEntity> findByRowCodeContainingOrderByCodeDesc(String rowCode, Pageable pageable);
	
	Page<LocationEntity> findByLevelCodeOrderByCodeDesc(Integer levelCode, Pageable pageable);
	
	Optional<LocationEntity> findByCode(String code);
	
	boolean existsByWarehouseCode(String warehouseCode);
}
