package com.quickkoala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseSummaryViewDto {
	private String orderNumber, productCode, name, quantity, price, totalPrice, orderDate;
	private int wtQuantity;
	
}
