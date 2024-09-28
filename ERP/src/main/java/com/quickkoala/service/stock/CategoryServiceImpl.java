package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.entity.stock.CategoryEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.repository.stock.CategoryRepository;
import com.quickkoala.repository.stock.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
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
	public CategoryEntity saveCategory(CategoryDto categorydto) {
		CategoryEntity categoryEntity = convertToCategoryEntity(categorydto);
		if(categoryRepository.existsByCode(categoryEntity.getCode())) {
		return null;
		}
		return categoryRepository.save(categoryEntity);
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
	
	@Override
	public CategoryDto getCategoryByCode(String code) {
		CategoryEntity categoryEntity= categoryRepository.findByCode(code);
		if(categoryEntity != null) {
		return convertToCategoryDto(categoryEntity);
		}
		return null;
	}
	
	@Override
	public boolean updateCategory(String Code, CategoryDto categoryDto) {
		CategoryEntity categoryEntity= categoryRepository.findById(Code).orElse(null);
		
		if(categoryEntity != null) {
			categoryEntity.setMainName(categoryDto.getMainName());
			categoryEntity.setSubName(categoryDto.getSubName());
			try {
			    categoryEntity.setUseFlag(UseFlag.valueOf(categoryDto.getUseFlag()));
			} catch (IllegalArgumentException e) {
			    throw new RuntimeException("유효하지 않은 useFlag값입니다.");
			}
			categoryEntity.setMemo(categoryDto.getMemo());
			categoryRepository.save(categoryEntity);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean candeleteCategory(String categoryCode) {
		int productCount = productRepository.countProductsByCategoryCode(categoryCode);
		return productCount == 0;
	}
	
	@Transactional
	@Override
	public Map<String, Object> deleteCategory(List<String> categoryCodes) {
		Map<String, Object> categoryDelresult = new HashMap<>();

		for(String code: categoryCodes) {
				 try {
			            Optional<CategoryEntity> optionalCategory = categoryRepository.findById(code);
			            
			            if (!optionalCategory.isPresent()) {
			                categoryDelresult.put(code, "카테고리를 찾을 수 없습니다.");
			                continue;
			            }

			            CategoryEntity categoryEntity = optionalCategory.get();

			            // 미사용 상태에서만 삭제 가능
			            if (!categoryEntity.getUseFlag().equals(UseFlag.N)) {
			                categoryDelresult.put(code, "카테고리가 미사용 상태일 때만 삭제 가능합니다.");
			                continue;
			            }

			            // 상품이 없을 때만 삭제 가능
			            if (candeleteCategory(code)) {
			                categoryRepository.deleteById(code);

			                // 데이터베이스 동기화 (명시적 flush)
			                categoryRepository.flush();  // 이 부분을 추가하여 즉시 삭제 반영

			                categoryDelresult.put(code, "삭제되었습니다.");
			            } else {
			                categoryDelresult.put(code, "카테고리에 제품이 있어 삭제할 수 없습니다.");
			            }

			        } catch (Exception e) {
			            // 예외 발생 시, 실제 예외 메시지를 로그로 출력
			            e.printStackTrace();  
			            categoryDelresult.put(code, "카테고리 삭제 중 오류 발생");
			        }
			    }
			    return categoryDelresult;
			}
	
	@Override
	public Page<CategoryEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return categoryRepository.findAllByOrderByCodeDesc(pageable);
	}
	
	@Override
	public Page<CategoryEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<CategoryEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = categoryRepository.findByCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = categoryRepository.findByMainCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("3")) {
			result = categoryRepository.findByMainNameContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("4")) {
			result = categoryRepository.findBySubCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("5")) {
			result = categoryRepository.findBySubNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
	}
	
	
}
