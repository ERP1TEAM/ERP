package com.quickkoala.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.WarehouseDto;
import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Override
	public List<WarehouseDto> getAllOrdersByCode() {

		return warehouseRepository.findAllByOrderByCodeDesc();
	}

	@Override
	public boolean saveWarehouse(WarehouseDto warehouseDto) {

		if(warehouseRepository.existsByCode(warehouseEntity.getCode())) {
			return false;
		}
		
		warehouseRepository.save(warehouseDto);
		return true;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteWarehouse(List<String> warehouseCodes) {
		Map<String, Object> warehousedelresult = new HashMap<>();
		
		for(String code: warehouseCodes) {
			try {
			if(warehouseRepository.existsById(code)) {
			warehouseRepository.deleteById(code);
			warehousedelresult.put(code, "삭제되었습니다");
			}else {
			warehousedelresult.put(code, "삭제에 실패했습니다.");
			}
        }catch(Exception e) {
        	warehousedelresult.put(code,"Error");
        }
		}
		
		return warehousedelresult;
	}
	@Override
	public WarehouseDto getWarehouseByCode(String warehouseCode) {
		return warehouseRepository.findByCode(warehouseCode);
	}
	
	@Override
	public boolean updateWarehouse(String warehouseCode, WarehouseDto warehouseDto) {

		WarehouseEntity warehouseupresult= warehouseRepository.findById(warehouseCode).orElse(null);
		
		if(warehouseupresult != null) {
			warehouseupresult.setName(warehouseDto.getName());
			warehouseupresult.setMemo(warehouseDto.getMemo());
		    warehouseRepository.save(warehouseupresult);
			return true;
		}
		
		return false;
	}
}
