package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.TestEntity;
import com.quickkoala.repository.TestRepository;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestRepository testRepository;
	
	@Override
	public List<TestEntity> getAllMember() {
		return testRepository.findAll();
	}
	
}
