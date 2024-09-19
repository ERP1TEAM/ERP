package com.quickkoala.dto.release;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseCancelDto {
	
	private String relNumber,orderNumber,who,reason,dt,manager,
	memo,salesCode,salesName;
}
