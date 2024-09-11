package com.quickkoala.service;

import java.util.List;
import java.util.Map;

import com.quickkoala.dto.WarehouseDto;
import com.quickkoala.entity.WarehouseEntity;

public interface WarehouseService {
	
	
	List<WarehouseDto> getAllOrdersByCode();
	
	boolean saveWarehouse(WarehouseDto warehouseDto);
	
	Map<String, Object> deleteWarehouse(List<String> warehouseCodes);
	
	WarehouseDto getWarehouseByCode(String warehouseCode);
	boolean updateWarehouse(String warehouseCode, WarehouseDto warehouseDto);
}
