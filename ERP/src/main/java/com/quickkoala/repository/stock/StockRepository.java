package com.quickkoala.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.StockEntity;
@Repository
public interface StockRepository extends JpaRepository<StockEntity, String>{

	StockEntity findByProductCode(String productCode);
}
