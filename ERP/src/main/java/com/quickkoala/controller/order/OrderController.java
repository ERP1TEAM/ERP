package com.quickkoala.controller.order;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quickkoala.service.order.OrderCancelService;
import com.quickkoala.service.order.OrderService;
import com.quickkoala.service.order.ViewOrderCancelService;
import com.quickkoala.service.order.ViewOrderOngoingService;
import com.quickkoala.service.order.ViewOrderProductsService;


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
