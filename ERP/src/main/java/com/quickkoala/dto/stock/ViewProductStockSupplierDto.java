package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewProductStockSupplierDto {

	private String productCode,productName,supplierCode,supplierName,locationCode,classificationCode,useFlag;
	private Integer price,totalQty,availableQty,unavailableQty,safetyQty;
}
