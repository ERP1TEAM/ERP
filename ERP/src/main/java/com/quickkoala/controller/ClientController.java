package com.quickkoala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.service.supplier.SupplierService;

@Controller
@RequestMapping("client")
public class ClientController {
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("supplierList")
	public String supplierList(Model model) {
		model.addAttribute("items",supplierService.getAllData());
		return "client/supplierList";
	}
}
