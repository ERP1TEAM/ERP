package com.quickkoala.service.stock;

import java.util.List;
import java.util.Map;

import com.quickkoala.dto.stock.WarehouseDto;
import com.quickkoala.entity.stock.WarehouseEntity;

public interface WarehouseService {
	
	
	List<WarehouseDto> getAllOrdersByCode();
	
	WarehouseEntity saveWarehouse(WarehouseDto warehouseDto);
	
	Map<String, Object> deleteWarehouse(List<String> warehouseCodes);
	
	WarehouseDto getWarehouseByCode(String warehouseCode);
	boolean updateWarehouse(String warehouseCode, WarehouseDto warehouseDto);
	
	//창고 검색
	List<WarehouseDto> searchWarehouse(String warehouseSearchtype,String warehouseSearch);
}
