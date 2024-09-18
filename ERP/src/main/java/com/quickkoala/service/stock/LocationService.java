package com.quickkoala.service.stock;

import java.util.List;
import java.util.Map;

import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.entity.stock.LocationEntity;

public interface LocationService {

	LocationEntity saveLocation (LocationDto locationdto);

	List<LocationDto> getAllOrdersByCode();
	
	Map<String, Object> deleteLocation(List<String> locationCodes);
}
