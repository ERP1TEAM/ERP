package com.quickkoala.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.client.SalesEntity;
import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.service.client.SalesService;
import com.quickkoala.service.client.SupplierService;

@RestController
@RequestMapping("main")
public class ClientRestController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SalesService salesService;
	
	// 발주처 리스트
	@GetMapping("client/supplierList/{pno}")
	public Page<SupplierEntity> supplierList(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word){
		int size = 5;
		Page<SupplierEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = supplierService.getPaginatedData(pno, size);
		}else {
			result = supplierService.getPaginatedData(pno, size, code, word);
		}
		return result;
	}
	
	// 거래처 리스트
	@GetMapping("client/salesList/{pno}")
	public Page<SalesEntity> salesList(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word){
		int size = 5;
		Page<SalesEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = salesService.getPaginatedData(pno, size);
		}else {
			result = salesService.getPaginatedData(pno, size, code, word);
		}
		return result;
	}
}
