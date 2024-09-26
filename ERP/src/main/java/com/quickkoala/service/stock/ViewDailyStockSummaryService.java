package com.quickkoala.service.stock;

import java.time.LocalDate;

import com.quickkoala.entity.stock.ViewDailyStockSummaryEntity;

public interface ViewDailyStockSummaryService{
	ViewDailyStockSummaryEntity getDailyStockSummary(LocalDate stockDate, String productCode);
}
