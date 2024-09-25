package com.quickkoala.service.stock;

public interface StockService {

	boolean updateSafetyQty(String productCode, int safetyQty);
}
