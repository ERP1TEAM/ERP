package com.quickkoala.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
	List<CategoryEntity> findAllByOrderByCodeDesc();
}
