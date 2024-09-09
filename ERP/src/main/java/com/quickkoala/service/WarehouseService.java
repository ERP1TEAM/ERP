package com.quickkoala.service;

import java.util.List;

import com.quickkoala.entity.WarehouseEntity;

public interface WarehouseService {
	List<WarehouseEntity> getAllOrdersByCode();
	
	void saveWarehouse(WarehouseEntity warehouseEntity);
}
