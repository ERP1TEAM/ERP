package com.quickkoala.service.receive;

import java.util.List;

import com.quickkoala.entity.receive.ViewLocationProductEntity;

public interface ViewLocationProductService {
	List<ViewLocationProductEntity> getData();
	int getCount(String productCode);
	String getLocationCode(String productCode);
}
