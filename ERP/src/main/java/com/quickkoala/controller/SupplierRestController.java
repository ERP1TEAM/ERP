package com.quickkoala.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.ViewDeliveryDetailEntity;
import com.quickkoala.entity.ViewDeliveryReturnEntity;
import com.quickkoala.entity.ViewReceiveReturnEntity;
import com.quickkoala.service.ViewDeliveryDetailService;
import com.quickkoala.service.ViewDeliveryReturnService;
import com.quickkoala.service.ViewReceiveReturnService;

@RestController
@RequestMapping("supplier")
public class SupplierRestController {
	
	@Autowired
	private ViewDeliveryDetailService viewDeliveryDetailService;
	
	@Autowired
	private ViewDeliveryReturnService viewDeliveryReturnService;
	
	//납품내역 리스트
	@GetMapping("deliveryData/{pno}")
	public Page<ViewDeliveryDetailEntity> deliveryData(@PathVariable Integer pno){
		int size = 5;
		return viewDeliveryDetailService.getPaginatedData(pno, size);
	}
	
	//반품내역 리스트
	@GetMapping("returnData/{pno}")
	public Page<ViewDeliveryReturnEntity> returnData(@PathVariable Integer pno){
		int size = 5;
		return viewDeliveryReturnService.getPaginadtedData(pno, size);
	}
}
