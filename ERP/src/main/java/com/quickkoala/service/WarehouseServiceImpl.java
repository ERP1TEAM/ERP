package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.repository.WarehouseRepository;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouserepository;
	
	@Override
	public List<WarehouseEntity> getAllOrdersByCode() {

		return warehouserepository.findAllByOrderByCodeDesc();
	}

	
}
