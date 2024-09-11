package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ReceiveTempViewEntity;
import com.quickkoala.repository.ReceiveTempViewRepository;

@Service
public class ReceiveTempViewServiceImpl implements ReceiveTempViewService{
	
	@Autowired
	private ReceiveTempViewRepository temporaryReceiveViewRepository;
	
	@Override
	public List<ReceiveTempViewEntity> getAllOrders(String status) {
		if(status == null) {
			return temporaryReceiveViewRepository.findAllByOrderByCodeDesc();			
		}else if(status.equals("처리대기")) {
			return temporaryReceiveViewRepository.findByWtQuantityNotOrderByCodeDesc(0);
		}else if(status.equals("처리완료")) {
			return temporaryReceiveViewRepository.findByWtQuantityOrderByCodeDesc(0);
		}
		return temporaryReceiveViewRepository.findAllByOrderByCodeDesc();
	}
}
