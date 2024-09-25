package com.quickkoala.service.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.stock.StockEntity;
import com.quickkoala.repository.stock.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Override
	public boolean updateSafetyQty(String productCode, int safetyQty) {

		StockEntity stockEntity = stockRepository.findByProductCode(productCode);
		
		if (stockEntity != null) {
            stockEntity.setSafetyQty(safetyQty);
            stockRepository.save(stockEntity);
            return true;
        }
		return false;
	    }
}
