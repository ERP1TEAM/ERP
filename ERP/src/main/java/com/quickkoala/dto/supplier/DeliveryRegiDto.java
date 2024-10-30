package com.quickkoala.dto.supplier;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRegiDto {

	@NotNull(message = "Data must not be null")
	private String data;
	
	@NotNull(message = "EA must not be null")
	private Integer ea;
}
