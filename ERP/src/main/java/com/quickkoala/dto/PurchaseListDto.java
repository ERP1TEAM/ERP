package com.quickkoala.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseListDto {
	private List<String> product_code, supplier, product;
	private List<Integer> quantity, price, total_price;
}
