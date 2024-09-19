package com.quickkoala.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.order.OrderOngoingDto;
import com.quickkoala.dto.order.OrderProductsDto;
import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.repository.order.ViewOrderProductsRepository;


@Service
public class ViewOrderProductsServiceImpl implements ViewOrderProductsService{
	
	@Autowired
	private ViewOrderProductsRepository viewOrderProductsRepository;
	
	@Override
	public List<ViewOrderProductsEntity> getAll(String onum) {
		return viewOrderProductsRepository.findByOrderNumberOrderByItemIdDesc(onum);
	}
	
}
