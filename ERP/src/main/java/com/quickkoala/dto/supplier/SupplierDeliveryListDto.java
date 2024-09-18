package com.quickkoala.dto.supplier;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDeliveryListDto {
	private List<String> product_code, product;
	private List<Integer> quantity, price, total_price;
}
