package com.quickkoala.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.entity.stock.CategoryEntity;

public interface CategoryService {

	List<CategoryDto> getAllOrdersByCode();
	
	CategoryEntity saveCategory (CategoryDto categorydto);
	
	CategoryDto getCategoryByCode(String code);
	
	boolean updateCategory(String Code, CategoryDto categoryDto);
	
	Map<String, Object> deleteCategory(List<String> categoryCodes);
	
	Page<CategoryEntity> getPaginatedData(int pno, int size);
	
	Page<CategoryEntity> getPaginatedData(int pno, int size, String code, String word);
}
