package com.quickkoala.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
	@GetMapping("distributor/order.do")
	public String order() {
		return "order/order";
	}
}
