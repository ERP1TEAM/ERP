package com.quickkoala.dto.receive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivingDto {
	private String orderNumber,deliveryCode,code,con,memo,location,productCode;
	private Integer wqty,reQty,caQty;
}
