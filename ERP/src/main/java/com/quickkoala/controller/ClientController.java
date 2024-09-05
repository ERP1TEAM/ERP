package com.quickkoala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.service.SupplierService;

@Controller
@RequestMapping("client")
public class ClientController {
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("supplierList")
	public String supplierList(Model model) {
		System.out.println("1번");
		model.addAttribute("items",supplierService.getAllData());
		System.out.println("2번");
		return "client/supplierList";
	}
}
