package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewProductStockDto {

	private String productCode,productName,supplierCode,locationCode,classificationCode,useFlag,manager,memo;
	private Integer price,totalQty,availableQty,unavailableQty,safetyQty;
}
