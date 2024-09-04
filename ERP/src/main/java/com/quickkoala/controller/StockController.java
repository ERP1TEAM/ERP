package com.quickkoala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("stock")
public class StockController {

	@GetMapping("/location")
	public String location() {
		
		return "stock/location.html";
	}
}
