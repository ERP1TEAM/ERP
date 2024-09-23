package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
	
	List<CategoryEntity> findAllByOrderByCodeDesc();
	
	boolean existsByCode (String code);
	
	CategoryEntity findByCode(String code);
	
	//코드,대소메뉴코드이름
	Page<CategoryEntity> findAllByOrderByCodeDesc(Pageable pageable);

	Page<CategoryEntity> findByCodeContainingOrderByCodeDesc(String code, Pageable pageable);

	Page<CategoryEntity> findByMainCodeContainingOrderByCodeDesc(String mainCode, Pageable pageable);
	
	Page<CategoryEntity> findByMainNameContainingOrderByCodeDesc(String mainName, Pageable pageable);
	
	Page<CategoryEntity> findBySubCodeContainingOrderByCodeDesc(String subCode, Pageable pageable);
	
	Page<CategoryEntity> findBySubNameContainingOrderByCodeDesc(String subName, Pageable pageable);
}
