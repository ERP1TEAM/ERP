package com.quickkoala.service.release;

import org.springframework.data.domain.Page;

import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;

public interface ViewReleaseReturnProductsService {
	
	Page<ViewReleaseReturnProductsEntity> getAll(int pg, int size,String select, String param);
	
}
