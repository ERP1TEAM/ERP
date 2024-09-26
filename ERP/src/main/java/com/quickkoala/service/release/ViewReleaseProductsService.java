package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.entity.release.ViewReleaseProductsEntity;

public interface ViewReleaseProductsService {
	
	public  List<ViewReleaseProductsEntity> getProducts(String relNumber);
	
}
