package com.quickkoala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.service.WarehouseService;

@Controller
@RequestMapping("warehouse")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/locationStatus")
	public String locationStatus() {
		
		return "warehouse/locationStatus";
	}
	
    @GetMapping("/warehousein-modal")
    public String warehousein() {
    	return "warehouse/warehouseModal :: warehouseInModalContent";
    }


    
}
