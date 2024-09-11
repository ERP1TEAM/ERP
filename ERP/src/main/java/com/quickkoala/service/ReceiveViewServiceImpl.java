package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ReceiveViewEntity;
import com.quickkoala.repository.ReceiveViewRepository;

@Service
public class ReceiveViewServiceImpl implements ReceiveViewService {

	@Autowired
	private ReceiveViewRepository receiveViewRepository;
	
	@Override
	public List<ReceiveViewEntity> getData() {
		return receiveViewRepository.findAllByOrderByOrderNumberDesc();
	}
}
