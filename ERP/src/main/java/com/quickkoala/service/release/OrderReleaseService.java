package com.quickkoala.service.release;


import java.util.List;

import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;

public interface OrderReleaseService{
	
	
	public String asignRelNumber();
	public String addReleaseFromOrder(OrderReleaseEntity entity);
	public List<ReleaseProductsEntity> asignLotNumber(String pcode, String scode, Integer qty, ReleaseProductsEntity entity);
	public String saveStatus(String id, String status,String manager);
}
