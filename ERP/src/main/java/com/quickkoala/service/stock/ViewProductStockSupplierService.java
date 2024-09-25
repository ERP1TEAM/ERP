package com.quickkoala.service.stock;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.stock.ViewProductStockSupplierDto;
import com.quickkoala.entity.stock.ViewProductStockSupplierEntity;

public interface ViewProductStockSupplierService {

	List<ViewProductStockSupplierDto> getAllOrdersByProductCode();
	ViewProductStockSupplierDto converToViewProductStockDto(ViewProductStockSupplierEntity viewProductStockEntity);
	
	Page<ViewProductStockSupplierEntity> getPaginatedData(int pno, int size);
	
	Page<ViewProductStockSupplierEntity> getPaginatedData(int pno, int size, String code, String word);
	
	Page<ViewProductStockSupplierEntity> getinventoryPaginatedData(int pno, int size, String code, String word);
}
