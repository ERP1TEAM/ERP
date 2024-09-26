package com.quickkoala.controller.order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("main")
public class OrderController {
		
	@GetMapping("order/ongoing")
	public String ongoing(Model m) {
		return "order/ongoing";
	}
	
	@GetMapping("order/cancel")
	public String cancel(Model m) {
		return "order/cancel";
	}
	
}
