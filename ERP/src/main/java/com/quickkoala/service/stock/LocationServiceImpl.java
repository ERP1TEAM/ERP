package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.repository.stock.LocationRepository;
import com.quickkoala.repository.stock.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
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
		
		return maptoLocationEntity;
	}
	
	@Override
	public List<LocationDto> getAllLocations() {
		
		List<LocationEntity> locationEntities = locationRepository.findAllByOrderByCodeAsc();
		List<LocationDto> locationDtos = new ArrayList<>();
		
		for (LocationEntity entity : locationEntities) {
		    LocationDto dto = convertToLocationDto(entity);
		    locationDtos.add(dto);
		}
		
		return locationDtos;
	}
	
	@Override
	public LocationDto getLocationByCode(String code) {
		LocationEntity locationEntity = locationRepository.findById(code).orElse(null);
		
		if (locationEntity == null) {
	        throw new IllegalArgumentException("해당 데이터의 로케이션 정보를 찾을 수 없습니다.");
	    }
		
		return convertToLocationDto(locationEntity);
	}
	
	@Override
	public LocationEntity updateLocation(LocationDto locationDto) {
	    Optional<LocationEntity> optionalLocationEntity = locationRepository.findByCode(locationDto.getCode());

	    if (!optionalLocationEntity.isPresent()) {
	        throw new IllegalArgumentException("해당 데이터의 로케이션 정보를 찾을 수 없습니다.");
	    }

	    LocationEntity locationEntity = optionalLocationEntity.get();
	   
	    UseFlag useFlag = UseFlag.valueOf(locationDto.getUseFlag());
	    locationEntity.setUseFlag(useFlag);
	    
	    return locationRepository.save(locationEntity);
	}
	
	@Override
	public LocationEntity saveLocation(LocationDto locationDto) {
		LocationEntity locationEntity =convertToLocationEntity(locationDto);
		
		if(locationRepository.existsByCode(locationEntity.getCode())) {
			throw new IllegalArgumentException("이미 존재하는 로케이션 코드입니다");
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
		            Optional<LocationEntity> optionalLocation = locationRepository.findById(code);
		            
		            if (!optionalLocation.isPresent()) {
		                locationDelresult.put(code, "로케이션을 찾을 수 없습니다.");
		                continue;
		            }
		            LocationEntity location = optionalLocation.get();

		            if (!location.getUseFlag().equals(UseFlag.N)) {
		                locationDelresult.put(code, "비활성화 상태에서만 삭제 가능합니다.");
		                continue;
		            }

		            int productCount = productRepository.countProductsByLocationCode(code);
		            
		            if (productCount > 0) {
		                locationDelresult.put(code, "해당 로케이션에 상품이 있어 삭제할 수 없습니다.");
		                continue;
		            }
		            
		            locationRepository.deleteById(code);
		            
		            if (locationRepository.existsById(code)) {
		                locationDelresult.put(code, "삭제에 실패했습니다.");
		            } else {
		                locationDelresult.put(code, "삭제되었습니다.");
		            }
		            
		            
	        }catch(Exception e) {
	        	locationDelresult.put(code,"삭제 중 오류발생하여 실패했습니다.");
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
