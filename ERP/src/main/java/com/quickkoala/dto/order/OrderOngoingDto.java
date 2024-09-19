package com.quickkoala.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOngoingDto {
	String number,orderId,status,salesCode,salesName, dt,approvedDt,manager, memo;
	int orderTotal;

}
