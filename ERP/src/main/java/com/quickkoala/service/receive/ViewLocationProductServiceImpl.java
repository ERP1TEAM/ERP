package com.quickkoala.service.receive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.receive.ViewLocationProductEntity;
import com.quickkoala.repository.receive.ViewLocationProductRepository;

@Service
public class ViewLocationProductServiceImpl implements ViewLocationProductService{

	@Autowired
	private ViewLocationProductRepository viewLocationProductRepository;
	
	@Override
	public List<ViewLocationProductEntity> getData() {
		return viewLocationProductRepository.findAllByOrderByLocationCode();
	}
	
	@Override
	public int getCount(String productCode) {
		return viewLocationProductRepository.countByProductCode(productCode);
	}
	
	@Override
	public String getLocationCode(String productCode) {
		String result = viewLocationProductRepository.findLocationCodeByProductCode(productCode).getLocationCode();
		return result;
	}
}
