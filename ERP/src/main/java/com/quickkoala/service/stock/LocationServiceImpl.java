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

import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.repository.stock.LocationRepository;

import jakarta.transaction.Transactional;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	
	
	
	
	//Entity -> DTO 변환
	@Override
	public LocationDto convertToLocationDto(LocationEntity locationEntity) {
		LocationDto maptoLocationDto = new LocationDto();
		maptoLocationDto.setCode(locationEntity.getCode());
		maptoLocationDto.setWarehouseCode(locationEntity.getWarehouseCode());
		maptoLocationDto.setRackCode(locationEntity.getRackCode());
		maptoLocationDto.setRowCode(locationEntity.getRowCode());
		maptoLocationDto.setLevelCode(locationEntity.getLevelCode());
		maptoLocationDto.setUseFlag(locationEntity.getUseFlag().name());
		maptoLocationDto.setMemo(locationEntity.getMemo());
		
		return maptoLocationDto;
	}
	
	
	//DTO -> Entity 변환
	private LocationEntity convertToLocationEntity(LocationDto locationDto) {
		
		LocationEntity maptoLocationEntity = new LocationEntity();
		maptoLocationEntity.setCode(locationDto.getCode());
		maptoLocationEntity.setWarehouseCode(locationDto.getWarehouseCode());
		maptoLocationEntity.setRackCode(locationDto.getRackCode());
		maptoLocationEntity.setRowCode(locationDto.getRowCode());
		maptoLocationEntity.setLevelCode(locationDto.getLevelCode());
		
		String useFlagValue = locationDto.getUseFlag();
		if (useFlagValue != null) {
		try {
		UseFlag useFlag = UseFlag.valueOf(locationDto.getUseFlag().toUpperCase());
		maptoLocationEntity.setUseFlag(useFlag);
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("잘못된 useFlag값 입니다.");
		}}else {
			throw new IllegalArgumentException("useFlag 값이 null입니다.");
		}
		
		maptoLocationEntity.setMemo(locationDto.getMemo());
		return maptoLocationEntity;
	}
	
	@Override
	public LocationEntity saveLocation(LocationDto locationDto) {

		LocationEntity locationEntity =convertToLocationEntity(locationDto);
		
		if(locationRepository.existsByCode(locationEntity.getCode())) {
			return null;
		}
		return locationRepository.save(locationEntity);
	}
	
	@Override
	public List<LocationDto> getAllOrdersByCode() {
		List<LocationEntity> listLocationEntity = locationRepository.findAllByOrderByCodeDesc();
		List<LocationDto> listLocationDto = new ArrayList<>();
		
		for (LocationEntity locationEntity : listLocationEntity) {
			LocationDto locationDto = convertToLocationDto(locationEntity);
			listLocationDto.add(locationDto);
		}
		return listLocationDto;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteLocation(List<String> locationCodes) {
		Map<String, Object> locationDelresult = new HashMap<>();
		
		for(String code : locationCodes) {
			try {
				if(locationRepository.existsById(code)) {
				locationRepository.deleteById(code);
				locationDelresult.put(code, "삭제되었습니다");
				}else {
				locationDelresult.put(code, "삭제에 실패했습니다.");
				}
	        }catch(Exception e) {
	        	locationDelresult.put(code,"Error");
	        }
		}
		
		return locationDelresult;
	}
	
	@Override
	public Page<LocationEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return locationRepository.findAllByOrderByCodeDesc(pageable);
	}
	
	@Override
	public Page<LocationEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<LocationEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = locationRepository.findByCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = locationRepository.findByWarehouseCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("3")) {
			result = locationRepository.findByRackCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("4")) {
			result = locationRepository.findByRowCodeContainingOrderByCodeDesc(word, pageable);
		}else if(code.equals("5")) {
			try {
				result = locationRepository.findByLevelCodeOrderByCodeDesc(Integer.valueOf(word), pageable);
			}catch(Exception e) {
				result = locationRepository.findAllByOrderByCodeDesc(pageable);
			}
		}
		return result;
	}
}
