package com.quickkoala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("stock")
public class StockController {
	
	@GetMapping("/inventoryIn")
	public String inventoryIn() {
		
		return "stock/inventoryIn";
	}
	
	@GetMapping("/inventoryList")
	public String inventoryList() {
		
		return "stock/inventoryList";
	}
	
}

