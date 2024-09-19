package com.quickkoala.controller.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class StockController {
	
	//재고
	
	@GetMapping("/stock/inventoryIn")
	public String inventoryIn() {
		
		return "stock/inventoryIn";
	}
	
	@GetMapping("/stock/inventoryList")
	public String inventoryList() {
		
		return "stock/inventoryList";
	}
	

	
	
	
	//창고,location
	
	@GetMapping("/stock/locationStatus")
	public String locationStatus() {
		return "stock/locationStatus";
	}
	
    @GetMapping("/stock/warehousein-modal")
    public String warehousein() {
    	return "stock/warehouseModal :: warehouseInModalContent";
    }
    
    @GetMapping("/stock/locationList")
    public String locationIn() {
		return "stock/locationList";
	}
    
}

