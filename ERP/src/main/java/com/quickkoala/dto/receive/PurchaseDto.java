package com.quickkoala.dto.receive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDto {
	private String product_code, supplier, product;
	private int quantity, price, total_price;
}
