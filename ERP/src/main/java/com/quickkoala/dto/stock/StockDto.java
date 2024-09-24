package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
	private String productCode,manager;
	private Integer totalQty,availableQty,unavailableQty,safetyQty;
}
