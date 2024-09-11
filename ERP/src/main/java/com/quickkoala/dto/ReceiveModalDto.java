package com.quickkoala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveModalDto {
	private String ornum, code, name;
	private Integer qty, wqty;
}
