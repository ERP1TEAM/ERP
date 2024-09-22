package com.quickkoala.service.release;

import java.util.List;

import com.quickkoala.entity.order.OrderEntity;

import jakarta.transaction.Transactional;

public interface ReleaseReturnProductsService {
	
	public String saveStatus(String relNum,String lotNum,int qty,String status);
	
}
