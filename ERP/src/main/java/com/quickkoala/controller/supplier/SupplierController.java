package com.quickkoala.controller.supplier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("supplier")
public class SupplierController {

	// 납품등록 페이지
	@GetMapping("supplierPurchaseList")
	public String supplierOrderList() {
		return "supplier/supplierPurchaseList";
	}

	// 납품내역 페이지
	@GetMapping("supplierDeliveryList")
	public String supplierDeliveryList() {
		return "supplier/supplierDeliveryList";
	}

	// 반품내역 페이지
	@GetMapping("supplierReturn")
	public String supplierReturn() {
		return "supplier/supplierReturn";
	}

}
