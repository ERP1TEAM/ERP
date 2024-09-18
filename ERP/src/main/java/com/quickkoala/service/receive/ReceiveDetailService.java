package com.quickkoala.service.receive;

import com.quickkoala.entity.receive.ReceiveDetailEntity;

public interface ReceiveDetailService {
	ReceiveDetailEntity addData(String data, Integer ea);
	long getCountOfOrdersToday();
}
