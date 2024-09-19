package com.quickkoala.controller.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.entity.sales.ClientsOrdersEntity;
import com.quickkoala.service.sales.OrderServiceImpl;

@Controller
@RequestMapping("/sales")
public class OrderController {
    
	@Autowired
	private OrderServiceImpl orderService;
	
	@GetMapping("/order")
	public String order() {
		return "sales/order";
	}
	
	@GetMapping("/orderlist")
	public String orderlist(Model model) {
        List<ClientsOrdersEntity> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "/sales/orderlist"; // Thymeleaf 템플릿 파일 이름 (orders.html)
	}
	
}
