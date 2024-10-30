package com.quickkoala.dto.receive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
	String code, word;
	String sDate, eDate;
	
	public boolean hasFilters() {
        return (sDate != null && !sDate.isEmpty()) || (word != null && !word.isEmpty());
    }
}
