package com.quickkoala.dto.receive;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailDto {
	String code, word;
	String sDate, eDate;
}
