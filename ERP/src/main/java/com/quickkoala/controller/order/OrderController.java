package com.quickkoala.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/distributor")
public class OrderController {
	@GetMapping("/order.do")
	public String order() {
		return "order/order";
	}
	
}
