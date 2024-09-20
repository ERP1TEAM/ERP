package com.quickkoala.service.stock;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.entity.stock.CategoryEntity;

public interface CategoryService {

	List<CategoryDto> getAllOrdersByCode();
	
	CategoryEntity saveCategory (CategoryDto categorydto);
	
	Page<CategoryEntity> getPaginatedData(int pno, int size);
	
	Page<CategoryEntity> getPaginatedData(int pno, int size, String code, String word);
}
