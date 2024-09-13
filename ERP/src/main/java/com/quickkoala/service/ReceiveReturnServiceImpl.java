package com.quickkoala.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.ReceiveReturnEntity;
import com.quickkoala.repository.ReceiveReturnRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class ReceiveReturnServiceImpl implements ReceiveReturnService{
	
	@Autowired
	private ReceiveReturnRepository receiveReturnRepository;
	
	@Override
	public ReceiveReturnEntity addData(ReceivingDto dto) {
		ReceiveReturnEntity data = new ReceiveReturnEntity();
		data.setCode("RT"+TodayUtils.getToday()+"");
		data.setOrderNumber(dto.getOrderNumber());
		data.setQuantity(dto.getCaQty());
		data.setReason(dto.getCon());
		data.setMemo(dto.getMemo());
		data.setDate(LocalDateTime.now());
		data.setManager(dto.getManager());
		
		
		return receiveReturnRepository.save(data);
	}
}
