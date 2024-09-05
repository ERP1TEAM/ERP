package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.TemporaryReceiveViewEntity;
import com.quickkoala.repository.TemporaryReceiveViewRepository;

@Service
public class TemporaryReceiveViewServiceImpl implements TemporaryReceiveViewService{
	
	@Autowired
	private TemporaryReceiveViewRepository temporaryReceiveViewRepository;
	
	@Override
	public List<TemporaryReceiveViewEntity> getAllOrders() {
		return temporaryReceiveViewRepository.findAllByOrderByCodeDesc();
	}
}
