package com.quickkoala.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelDto {
	private String orderNumber,orderId,salesCode,salesName,dt,manager,memo;
	private int orderTotal;
	

}
