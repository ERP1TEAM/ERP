package com.quickkoala.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class ClientController {
	
	@GetMapping("client/supplierList")
	public String supplierList() {
		return "client/supplierList";
	}
	
	@GetMapping("client/salesList")
	public String salesList() {
		return "client/salesList";
	}
}
