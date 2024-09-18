package com.quickkoala.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {
	private String code,warehouseCode,rackCode,rowCode,useFlag,memo;
	private Integer levelCode;
}
