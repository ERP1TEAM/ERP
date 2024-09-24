package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ViewLotViewProductStockSupplierEntity;
@Repository
public interface ViewLotViewProductStockSupplierRepository extends JpaRepository<ViewLotViewProductStockSupplierEntity, String> {
	List<ViewLotViewProductStockSupplierEntity> findByLocationCode(String locationCode);
}
