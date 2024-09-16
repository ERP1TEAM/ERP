package com.quickkoala.service;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.ReceiveReturnEntity;

public interface ReceiveReturnService {
	ReceiveReturnEntity addData(ReceivingDto dto);
	public long countReturnsToday();
}
