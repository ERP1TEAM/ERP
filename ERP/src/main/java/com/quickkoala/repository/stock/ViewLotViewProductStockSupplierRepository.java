package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ViewLotViewProductStockSupplierEntity;
@Repository
public interface ViewLotViewProductStockSupplierRepository extends JpaRepository<ViewLotViewProductStockSupplierEntity, String> {
	
	@Query("SELECT v FROM ViewLotViewProductStockSupplierEntity v WHERE TRIM(v.locationCode) = :locationCode")
	List<ViewLotViewProductStockSupplierEntity> findByTrimmedLocationCode(@Param("locationCode") String locationCode);
}
