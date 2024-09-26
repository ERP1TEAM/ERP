package com.quickkoala.repository.stock;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.stock.ViewDailyStockSummaryEntity;

@Repository
public interface ViewDailyStockSummaryRepository extends JpaRepository<ViewDailyStockSummaryEntity, String>{
	
	Optional<ViewDailyStockSummaryEntity> findByStockDateAndProductCode(LocalDate stockDate, String productCode);

}
