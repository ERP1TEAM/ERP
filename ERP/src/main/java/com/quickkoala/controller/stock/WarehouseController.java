package com.quickkoala.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.service.stock.WarehouseService;

@Controller
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/stock/locationStatus")
	public String locationStatus() {
		
		return "stock/locationStatus";
	}
	
    @GetMapping("/stock/warehousein-modal")
    public String warehousein() {
    	return "stock/warehouseModal :: warehouseInModalContent";
    }


    
}
