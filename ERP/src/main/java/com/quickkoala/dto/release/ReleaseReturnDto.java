package com.quickkoala.dto.release;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseReturnDto {
	String relNumber,lotNumber,manager,dt,productCode,productName,supplierCode,supplierName,reason,status,salesName,salesCode;
	int idx, qty, price;;
}
