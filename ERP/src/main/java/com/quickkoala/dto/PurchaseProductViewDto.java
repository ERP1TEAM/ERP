package com.quickkoala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseProductViewDto {
	private String orderNumber, productCode, name, quantity, price, totalPrice, orderDate, wtQuantity;
	
}
