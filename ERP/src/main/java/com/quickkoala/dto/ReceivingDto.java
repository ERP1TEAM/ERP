package com.quickkoala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivingDto {
	private String orderNumber,deliveryCode,code,con,memo,manager;
	private Integer wqty,reQty,caQty;
}
