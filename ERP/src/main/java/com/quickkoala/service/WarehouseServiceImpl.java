package com.quickkoala.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Override
	public List<WarehouseEntity> getAllOrdersByCode() {

		return warehouseRepository.findAllByOrderByCodeDesc();
	}

	@Override
	public boolean saveWarehouse(WarehouseEntity warehouseEntity) {

		if(warehouseRepository.existsByCode(warehouseEntity.getCode())) {
			return false;
		}
		
		warehouseRepository.save(warehouseEntity);
		return true;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteWarehouse(List<String> warehouseCode) {
		Map<String, Object> response = new HashMap<>();
		List<String> faildel = new ArrayList<>();
        List<String> successdel = new ArrayList<>();
		
		for(String code: warehouseCode) {
			try {
			warehouseRepository.deleteByCode(code);
			successdel.add(code);
        }catch(Exception e) {
        	faildel.add(code);
        }
		}
		response.put("ok", successdel); 
		response.put("no", faildel); 
		
		return response;
	}
	
}
