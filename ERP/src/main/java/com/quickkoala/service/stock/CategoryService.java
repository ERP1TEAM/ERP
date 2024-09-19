package com.quickkoala.service.stock;

import java.util.List;

import com.quickkoala.dto.stock.CategoryDto;

public interface CategoryService {

	List<CategoryDto> getAllOrdersByCode();
	
}
