package com.quickkoala.service.stock;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.stock.ViewDailyStockSummaryEntity;
import com.quickkoala.repository.stock.ViewDailyStockSummaryRepository;

@Service
public class ViewDailyStockSummaryServiceImpl implements ViewDailyStockSummaryService {

	@Autowired
	private ViewDailyStockSummaryRepository viewDailyStockSummaryRepository;
	
	@Override
	public ViewDailyStockSummaryEntity getDailyStockSummary(LocalDate stockDate, String productCode) {
		
		Optional<ViewDailyStockSummaryEntity> optionalEntity = viewDailyStockSummaryRepository.findByStockDateAndProductCode(stockDate, productCode);
		
		if(optionalEntity.isPresent()){
			
			return optionalEntity.get();
		}else {
			throw new RuntimeException("정보를 불러오지 못했습니다.");
		}
	}
}
