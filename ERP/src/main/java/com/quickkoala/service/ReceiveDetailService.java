package com.quickkoala.service;

import com.quickkoala.entity.ReceiveDetailEntity;

public interface ReceiveDetailService {
	ReceiveDetailEntity addData(String data, Integer ea);
	long getCountOfOrdersToday();
}
