package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;

public interface ViewReleaseCompleteProductsService {
	
	public  List<ViewReleaseCompleteProductsEntity> getProducts(String relNumber);
	
}
