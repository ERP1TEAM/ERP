package com.quickkoala.dto.receive;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveModalDto {
	private String ornum, code, name, deli, productCode;
	private Integer qty, wqty;
	private List<String> location;
}
