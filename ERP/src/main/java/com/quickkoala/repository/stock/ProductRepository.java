package com.quickkoala.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String>{

	boolean existsByCode(String code);
}
