package com.quickkoala.dto.receive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveModalDto {
	private String ornum, code, name, deli;
	private Integer qty, wqty;
}
