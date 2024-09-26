package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;

import jakarta.transaction.Transactional;

public interface ViewReleaseCompleteProductsService {
	
	public  List<ViewReleaseCompleteProductsEntity> getProducts(String relNumber);
	
}
