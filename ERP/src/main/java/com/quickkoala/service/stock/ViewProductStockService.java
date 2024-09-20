package com.quickkoala.service.stock;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.stock.ViewProductStockDto;
import com.quickkoala.entity.stock.ViewProductStockEntity;

public interface ViewProductStockService {

	List<ViewProductStockDto> getAllOrdersByProductCode();
	ViewProductStockDto converToViewProductStockDto(ViewProductStockEntity viewProductStockEntity);
	
	Page<ViewProductStockEntity> getPaginatedData(int pno, int size);
	
	Page<ViewProductStockEntity> getPaginatedData(int pno, int size, String code, String word);
}
