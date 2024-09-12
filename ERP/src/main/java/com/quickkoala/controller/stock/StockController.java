package com.quickkoala.controller.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class StockController {
	
	@GetMapping("/stock/inventoryIn")
	public String inventoryIn() {
		
		return "stock/inventoryIn";
	}
	
	@GetMapping("/stock/inventoryList")
	public String inventoryList() {
		
		return "stock/inventoryList";
	}
	
}

