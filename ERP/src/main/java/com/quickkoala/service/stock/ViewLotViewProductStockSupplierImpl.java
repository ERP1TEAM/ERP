package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.ViewLotViewProductStockSupplierDto;
import com.quickkoala.entity.stock.ViewLotViewProductStockSupplierEntity;
import com.quickkoala.repository.stock.ViewLotViewProductStockSupplierRepository;

@Service
public class ViewLotViewProductStockSupplierImpl implements ViewLotViewProductStockSupplierService{

	@Autowired
	private ViewLotViewProductStockSupplierRepository viewlotViewpssrepository;
	
	
	public ViewLotViewProductStockSupplierDto convertToDto(ViewLotViewProductStockSupplierEntity entity) {
        ViewLotViewProductStockSupplierDto dto = new ViewLotViewProductStockSupplierDto();
        dto.setProductCode(entity.getProductCode());
        dto.setProductName(entity.getProductName());
        dto.setSupplierCode(entity.getSupplierCode());
        dto.setSupplierName(entity.getSupplierName());
        dto.setLocationCode(entity.getLocationCode());
        dto.setSafetyQty(entity.getSafetyQty());
        dto.setLotQuantity(entity.getLotQuantity());
        return dto;
    }
    
	@Override
public List<ViewLotViewProductStockSupplierDto> getAllProductsByLocation(String locationCode) {
		List<ViewLotViewProductStockSupplierEntity> entities = viewlotViewpssrepository.findByLocationCode(locationCode);
		
		List<ViewLotViewProductStockSupplierDto> dtoList = new ArrayList<>();
		
		for (ViewLotViewProductStockSupplierEntity entity : entities) {

		    ViewLotViewProductStockSupplierDto dto = convertToDto(entity);

		    dtoList.add(dto);
		}
		
		return dtoList;
}
}
