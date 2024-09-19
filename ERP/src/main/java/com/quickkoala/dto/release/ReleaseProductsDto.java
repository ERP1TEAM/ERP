package com.quickkoala.dto.release;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseProductsDto {
	String relNumber,lotNumber,dt,manager,memo;
	int qty, idx;

}
