package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;

import jakarta.transaction.Transactional;

public interface ViewReleaseProductsService {
	
	public  List<ViewReleaseProductsEntity> getProducts(String relNumber);
	
}
