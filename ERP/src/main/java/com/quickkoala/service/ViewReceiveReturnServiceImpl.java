package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewReceiveReturnEntity;
import com.quickkoala.repository.ViewReceiveReturnRepository;

@Service
public class ViewReceiveReturnServiceImpl implements ViewReceiveReturnService{

	@Autowired
	private ViewReceiveReturnRepository viewReceiveReturnRepository;
	
	@Override
	public List<ViewReceiveReturnEntity> getAllData() {
		return viewReceiveReturnRepository.findAllByOrderByReturnDateDesc();
	}
}
