package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	private String code,supplierCode,classificationCode,storageLocation;
	private String name,manager,memo;
}
