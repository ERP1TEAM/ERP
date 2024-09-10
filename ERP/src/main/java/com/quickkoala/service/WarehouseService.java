package com.quickkoala.service;

import java.util.List;
import java.util.Map;

import com.quickkoala.entity.WarehouseEntity;

public interface WarehouseService {
	
	
	List<WarehouseEntity> getAllOrdersByCode();
	
	boolean saveWarehouse(WarehouseEntity warehouseEntity);
	
	Map<String, Object> deleteWarehouse(List<String> warehouseCode);
}
