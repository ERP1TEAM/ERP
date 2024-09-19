package com.quickkoala.service.stock;

import java.util.List;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.entity.stock.CategoryEntity;

public interface CategoryService {

	List<CategoryDto> getAllOrdersByCode();
	
	CategoryEntity saveCategory (CategoryDto categorydto);
}
