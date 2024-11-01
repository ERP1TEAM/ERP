package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.WarehouseDto;
import com.quickkoala.entity.stock.WarehouseEntity;
import com.quickkoala.repository.stock.LocationRepository;
import com.quickkoala.repository.stock.WarehouseRepository;

import jakarta.transaction.Transactional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private LocationRepository locationRepository;
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
		List<WarehouseEntity> listWarehouseEntity = warehouseRepository.findAllByOrderByCode();
		List<WarehouseDto> listWarehouseDto = new ArrayList<>();
		
		for (WarehouseEntity warehouseEntity : listWarehouseEntity) {
			WarehouseDto warehouseDto = convertToWarehouseDto(warehouseEntity);
			listWarehouseDto.add(warehouseDto);
		}
	return listWarehouseDto;
	}

	@Override
	public WarehouseEntity saveWarehouse(WarehouseDto warehouseDto) {
		
		WarehouseEntity warehouseEntity = convertToWarehouseEntity(warehouseDto);

		if(warehouseRepository.existsByCode(warehouseEntity.getCode())) {
			return null;
		}
		
		return warehouseRepository.save(warehouseEntity);
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteWarehouse(List<String> warehouseCodes) {
		Map<String, Object> warehouseDelresult = new HashMap<>();
		
		for(String code: warehouseCodes) {
			try {
	            if (warehouseRepository.existsById(code)) {
	                
	                if (locationRepository.existsByWarehouseCode(code)) {
	                    warehouseDelresult.put(code, "해당 창고에 로케이션이 남아있어 삭제할 수 없습니다.");
	                } else {
	                    warehouseRepository.deleteById(code);
	                    warehouseDelresult.put(code, "삭제되었습니다.");
	                }
	                
	            } else {
	                warehouseDelresult.put(code, "존재하지 않는 창고입니다.");
	            }
	        } catch (Exception e) {
	            warehouseDelresult.put(code, "삭제 중 오류가 발생했습니다.");
	        }
	    }

	    return warehouseDelresult;
	}
	
	@Override
	public boolean hasLocations(String warehouseCode) {
		return locationRepository.existsByWarehouseCode(warehouseCode);
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
	
	//창고검색
	@Override
	public List<WarehouseDto> searchWarehouse(String warehouseSearchtype, String warehouseSearch) {
		
		List<WarehouseEntity> warehouseSearchresult= new ArrayList<>();
			
		//이름
		if("1".equals(warehouseSearchtype)) {
			warehouseSearchresult = warehouseRepository.findByNameContaining(warehouseSearch);
		}
		//코드
		else if("2".equals(warehouseSearchtype)) {
			warehouseSearchresult=warehouseRepository.findByCodeContaining(warehouseSearch);
		}
		List<WarehouseDto> warehouseDto = new ArrayList<>();
		for(WarehouseEntity warehouseEntity : warehouseSearchresult) {
			warehouseDto.add(convertToWarehouseDto(warehouseEntity));
		}
		return warehouseDto;
		}
	
	
	@Override
	public Page<WarehouseEntity> getPaginatedData(int pno, int size) {
		
		Pageable pageable = PageRequest.of(pno-1, size);
		return warehouseRepository.findAllByOrderByCodeDesc(pageable);
		
	}
	@Override
	public Page<WarehouseEntity> getPaginatedData(int pno, int size, String code, String word) {
		
		Page<WarehouseEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = warehouseRepository.findByCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = warehouseRepository.findByNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
		
	}
	
}
