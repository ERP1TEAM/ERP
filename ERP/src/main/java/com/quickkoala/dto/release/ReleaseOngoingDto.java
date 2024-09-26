package com.quickkoala.dto.release;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReleaseOngoingDto {
	private String number,orderNumber,orderId,salesCode,salesName,dt,status,manager,memo;
	

}
