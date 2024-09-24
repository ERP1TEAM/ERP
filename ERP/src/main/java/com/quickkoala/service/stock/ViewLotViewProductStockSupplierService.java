package com.quickkoala.service.stock;

import java.util.List;

import com.quickkoala.dto.stock.ViewLotViewProductStockSupplierDto;
public interface ViewLotViewProductStockSupplierService{
	List<ViewLotViewProductStockSupplierDto> getAllProductsByLocation(String locationCode);
}
