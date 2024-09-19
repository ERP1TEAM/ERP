package com.quickkoala.service;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.stock.LotEntity;

public interface LotService {
	LotEntity addLot(ReceivingDto dto);
}
