package com.quickkoala.service.order;

import java.util.List;

import com.quickkoala.entity.order.ViewOrderProductsEntity;

public interface ViewOrderProductsService {
	
	List<ViewOrderProductsEntity> getAll(String onum);
	
}
