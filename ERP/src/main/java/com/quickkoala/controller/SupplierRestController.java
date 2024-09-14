package com.quickkoala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.ViewDeliveryDetailEntity;
import com.quickkoala.service.ViewDeliveryDetailService;

@RestController
@RequestMapping("supplier")
public class SupplierRestController {
	
	@Autowired
	private ViewDeliveryDetailService viewDeliveryDetailService;
	
	@GetMapping("deliveryData/{pno}")
	public Page<ViewDeliveryDetailEntity> deliveryData(@PathVariable Integer pno){
		int size = 3;
		return viewDeliveryDetailService.getPaginatedData(pno, size);
	}
}
