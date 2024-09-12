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
	
	//Entity -> DTO 변환
	private WarehouseDto convertToWarehouseDto(WarehouseEntity warehouseEntity) {
		WarehouseDto maptoWarehouseDto = new WarehouseDto();
		maptoWarehouseDto.setCode(warehouseEntity.getCode());
		maptoWarehouseDto.setName(warehouseEntity.getName());
		maptoWarehouseDto.setMemo(warehouseEntity.getMemo());
		return maptoWarehouseDto;
	}
	
	//DTO -> Entity 변환
	private WarehouseEntity convertToWarehouseEntity(WarehouseDto warehouseDto) {
		WarehouseEntity maptoWarehouseEntity = new WarehouseEntity();
		maptoWarehouseEntity.setCode(warehouseDto.getCode());
		maptoWarehouseEntity.setName(warehouseDto.getName());
		maptoWarehouseEntity.setMemo(warehouseDto.getMemo());
		return maptoWarehouseEntity;
	}
	
	@Override
	public List<WarehouseDto> getAllOrdersByCode() {
		List<WarehouseEntity> listWarehouseEntity = warehouseRepository.findAllByOrderByCodeDesc();
		List<WarehouseDto> listWarehouseDto = new ArrayList<>();
		
		for (WarehouseEntity warehouseEntity : listWarehouseEntity) {
			WarehouseDto warehouseDto = convertToWarehouseDto(warehouseEntity);
			listWarehouseDto.add(warehouseDto);
		}
	return listWarehouseDto;
	}

	@Override
	public boolean saveWarehouse(WarehouseDto warehouseDto) {
		
		WarehouseEntity warehouseEntity = convertToWarehouseEntity(warehouseDto);

		if(warehouseRepository.existsByCode(warehouseEntity.getCode())) {
			return false;
		}
		
		warehouseRepository.save(warehouseEntity);
		return true;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteWarehouse(List<String> warehouseCodes) {
		Map<String, Object> warehouseDelresult = new HashMap<>();
		
		for(String code: warehouseCodes) {
			try {
			if(warehouseRepository.existsById(code)) {
			warehouseRepository.deleteById(code);
			warehouseDelresult.put(code, "삭제되었습니다");
			}else {
			warehouseDelresult.put(code, "삭제에 실패했습니다.");
			}
        }catch(Exception e) {
        	warehouseDelresult.put(code,"Error");
        }
		}
		
		return warehouseDelresult;
	}
	@Override
	public WarehouseDto getWarehouseByCode(String warehouseCode) {
		WarehouseEntity warehouseEntity = warehouseRepository.findByCode(warehouseCode);
		if(warehouseEntity != null) {
			return convertToWarehouseDto(warehouseEntity);
		}
		
		
		return null;
	}
	
	@Override
	public boolean updateWarehouse(String warehouseCode, WarehouseDto warehouseDto) {

		WarehouseEntity warehouseEntity= warehouseRepository.findById(warehouseCode).orElse(null);
		
		if(warehouseEntity != null) {
			warehouseEntity.setName(warehouseDto.getName());
			warehouseEntity.setMemo(warehouseDto.getMemo());
		    warehouseRepository.save(warehouseEntity);
			return true;
		}
		
		return false;
	}
}
