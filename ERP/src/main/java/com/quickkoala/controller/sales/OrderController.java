package com.quickkoala.controller.sales;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class OrderController {
	@GetMapping("/order.do")
	public String order() {
		return "sales/order";
	}
	
}
