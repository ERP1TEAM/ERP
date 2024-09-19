package com.quickkoala.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.entity.stock.LocationEntity;

public interface LocationService {

	LocationEntity saveLocation (LocationDto locationdto);

	List<LocationDto> getAllOrdersByCode();
	
	Map<String, Object> deleteLocation(List<String> locationCodes);
	
	Page<LocationEntity> getPaginatedData(int pno, int size);
	
	Page<LocationEntity> getPaginatedData(int pno, int size, String code, String word);
}
