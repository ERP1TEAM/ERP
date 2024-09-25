package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLotViewProductStockSupplierDto {
private String lotNumber,productCode,productName,supplierCode,supplierName,locationCode;
private Integer lotQuantity,safetyQty;
}
