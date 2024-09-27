package com.quickkoala.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	private static final int SIZE = 10;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SalesService salesService;
	
	// 발주처 리스트
	@GetMapping("client/supplierList/{pno}")
	public ResponseEntity<Page<SupplierEntity>> supplierList(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word){
		Page<SupplierEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = supplierService.getPaginatedData(pno, SIZE);
		}else {
			result = supplierService.getPaginatedData(pno, SIZE, code, word);
		}
		return ResponseEntity.ok(result);
	}
	
	// 거래처 리스트
	@GetMapping("client/salesList/{pno}")
	public ResponseEntity<Page<SalesEntity>> salesList(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word){
		Page<SalesEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = salesService.getPaginatedData(pno, SIZE);
		}else {
			result = salesService.getPaginatedData(pno, SIZE, code, word);
		}
		return ResponseEntity.ok(result);
	}
	
	// 거래처 등록
	@PostMapping("client/addSales")
	public ResponseEntity<String> addSales(@ModelAttribute SalesEntity data) {
		SalesEntity salesEntity = salesService.addSales(data);
		if(salesEntity == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		}
		return ResponseEntity.ok("ok");
	}
	
	// 발주처 등록
	@PostMapping("client/addSupplier")
	public ResponseEntity<String> addSupplier(@ModelAttribute SupplierEntity data) {
		SupplierEntity supplierEntity = supplierService.addSupplier(data);
		if(supplierEntity == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		}
		return ResponseEntity.ok("ok");
	}
	
	// 거래처 수정 데이터
	@GetMapping("client/salesOne/{code}")
	public SalesEntity salesOne(@PathVariable String code) {
		return salesService.getOne(code);
	}
	
	// 거래처 수정 확정
	@PatchMapping("client/modifySales/{code}")
	public ResponseEntity<String> modifySales(@PathVariable String code, @ModelAttribute SalesEntity data) {
		SalesEntity salesEntity = salesService.modifySales(data, code);
		if(salesEntity == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		}
		return ResponseEntity.ok("ok");
	}
	
	// 발주처 수정 데이터
	@GetMapping("client/supplierOne/{code}")
	public ResponseEntity<SupplierEntity> supplierOne(@PathVariable String code) {
		return ResponseEntity.ok(supplierService.getOne(code));
	}
	
	// 거래처 수정 확정
	@PatchMapping("client/modifySupplier/{code}")
	public ResponseEntity<String> modifysupplier(@PathVariable String code, @ModelAttribute SupplierEntity data) {
		SupplierEntity supplierEntity = supplierService.modifySupplier(data, code);
		if(supplierEntity == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		}
		return ResponseEntity.ok("ok");
	}
}
