package com.quickkoala.dto.supplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDeliveryDto {
	private String product_code, product;
	private int quantity, price, total_price;
}
