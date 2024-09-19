package com.quickkoala.service.release;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;

public interface OrderReleaseService{
	
	
	public String getMaxNumber();
	public void addReleaseFromOrder(OrderReleaseEntity entity);
	public void addAllReleaseFromOrder(List<OrderReleaseEntity> list);
	public List<ReleaseProductsEntity> asignLotNumber(String pcode, String scode, Integer qty, ReleaseProductsEntity entity);
	public String saveStatus(String id,String status);
}
