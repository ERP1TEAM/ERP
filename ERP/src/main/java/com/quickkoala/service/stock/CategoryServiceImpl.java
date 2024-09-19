package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.entity.stock.CategoryEntity;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.repository.stock.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Entity -> DTO 변환
	private CategoryDto convertToCategoryDto(CategoryEntity categoryEntity) {
		CategoryDto maptoCategoryDto = new CategoryDto();
		maptoCategoryDto.setCode(categoryEntity.getCode());
		maptoCategoryDto.setMainCode(categoryEntity.getMainCode());
		maptoCategoryDto.setMainName(categoryEntity.getMainName());
		maptoCategoryDto.setSubCode(categoryEntity.getSubCode());
		maptoCategoryDto.setSubName(categoryEntity.getSubName());
		maptoCategoryDto.setUseFlag(categoryEntity.getUseFlag().name());
		maptoCategoryDto.setMemo(categoryEntity.getMemo());
		return maptoCategoryDto;
	}
	
	//DTO -> Entity 변환
	private CategoryEntity convertToCategoryEntity(CategoryDto categoryDto) {
		CategoryEntity maptoCategoryEntity = new CategoryEntity();
		maptoCategoryEntity.setCode(categoryDto.getCode());
		maptoCategoryEntity.setMainCode(categoryDto.getMainCode());
		maptoCategoryEntity.setMainName(categoryDto.getMainName());
		maptoCategoryEntity.setSubCode(categoryDto.getSubCode());
		maptoCategoryEntity.setSubName(categoryDto.getSubName());
		
		String categoryuseFlagValue = categoryDto.getUseFlag();
		if (categoryuseFlagValue!= null) {
			try {
			UseFlag useFlag = UseFlag.valueOf(categoryDto.getUseFlag().toUpperCase());
			maptoCategoryEntity.setUseFlag(useFlag);
			}catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("잘못된 카테고리 useFlag값 입니다.");
			}}else {
				throw new IllegalArgumentException("카테고리 useFlag 값이 null입니다.");
			}
		maptoCategoryEntity.setMemo(categoryDto.getMemo());
		return maptoCategoryEntity;
	}
	
	@Override
	public List<CategoryDto> getAllOrdersByCode() {
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAllByOrderByCodeDesc();
		List<CategoryDto> listCategoryDto = new ArrayList<>();
		
		for (CategoryEntity categoryEntity : listCategoryEntity ) {
			CategoryDto categoryDto = convertToCategoryDto(categoryEntity);
			listCategoryDto.add(categoryDto);
		}
		return listCategoryDto;
	}
	
	
}
