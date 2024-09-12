package com.quickkoala.service.stock;

import java.util.List;
import java.util.Map;

import com.quickkoala.dto.stock.WarehouseDto;
import com.quickkoala.entity.stock.WarehouseEntity;

public interface WarehouseService {
	
	
	List<WarehouseDto> getAllOrdersByCode();
	
	boolean saveWarehouse(WarehouseDto warehouseDto);
	
	Map<String, Object> deleteWarehouse(List<String> warehouseCodes);
	
	WarehouseDto getWarehouseByCode(String warehouseCode);
	boolean updateWarehouse(String warehouseCode, WarehouseDto warehouseDto);
}
