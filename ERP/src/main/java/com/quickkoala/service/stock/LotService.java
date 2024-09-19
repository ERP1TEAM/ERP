package com.quickkoala.service.stock;

import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.entity.stock.LotEntity;

public interface LotService {
	LotEntity addLot(ReceivingDto dto);
}
