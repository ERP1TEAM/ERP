package com.quickkoala.service.receive;

import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.entity.receive.ReceiveReturnEntity;

public interface ReceiveReturnService {
	ReceiveReturnEntity addData(ReceivingDto dto, String manager);
	public long countReturnsToday();
}
