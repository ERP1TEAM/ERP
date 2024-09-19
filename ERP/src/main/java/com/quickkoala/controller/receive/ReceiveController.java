package com.quickkoala.controller.receive;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.dto.receive.PurchaseDto;
import com.quickkoala.dto.receive.ReceiveModalDto;
import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.service.client.SupplierService;
import com.quickkoala.service.receive.ViewPurchaseService;
import com.quickkoala.service.supplier.PurchaseService;
import com.quickkoala.utils.ExcelUpload;

@Controller
@RequestMapping("main")
public class ReceiveController {
	
	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ViewPurchaseService purchaseViewService;

	// 발주요청 페이지
	@GetMapping("receive/purchaseOrder")
	public String orderPage(Model model) {
		model.addAttribute("items", purchaseViewService.getAllData());
		return "receive/purchaseOrder";
	}

	// 발주내역 페이지
	@GetMapping("receive/purchaseDetail")
	public String listPage() {
		return "receive/purchaseDetail";
	}

	// 가입고 페이지
	@GetMapping("receive/temporaryReceive")
	public String tempPage() {
		return "receive/temporaryReceive";
	}

	// 입고현황 페이지
	@GetMapping("receive/receiveSummary")
	public String receiveSummary() {
		return "receive/receiveSummary";
	}

	// 입고내역 페이지
	@GetMapping("receive/receiveDetail")
	public String receiveDetail() {
		return "receive/receiveDetail";
	}

	// 반품내역 페이지
	@GetMapping("receive/receiveReturn")
	public String receiveReturn() {
		return "receive/receiveReturn";
	}

	// 발주요청
	@PostMapping("receive/purchaseAdd")
	public ResponseEntity<String> purchaseAdd(@ModelAttribute PurchaseListDto orders) {
		purchaseService.addOrders(orders);
		return ResponseEntity.ok("success");
	}

	// 입고확정 모달
	@GetMapping("receive/receivingModal")
	public ResponseEntity<ReceiveModalDto> receivingModal(@RequestParam("ornum") String ornum,
			@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("qty") Integer qty,
			@RequestParam("wqty") Integer wqty, @RequestParam("deli") String deli) {
		ReceiveModalDto dto = new ReceiveModalDto();
		dto.setOrnum(ornum);
		dto.setCode(code);
		dto.setDeli(deli);
		dto.setName(name);
		dto.setQty(qty);
		dto.setWqty(wqty);
		return ResponseEntity.ok(dto);
	}

	// 제조사 자동완성
	@GetMapping("receive/autocomplete")
	public ResponseEntity<List<SupplierEntity>> autocomplete(@RequestParam String term) {
		return ResponseEntity.ok(supplierService.searchByName(term));
	}

	// 엑셀 불러오기
	@PostMapping("receive/upload-excel")
	public ResponseEntity<List<PurchaseDto>> uploadExcel(@RequestParam("excel") MultipartFile file) {
		List<PurchaseDto> data = new ArrayList<>();
		ExcelUpload eu = new ExcelUpload();

		for (Row row : eu.uploadExcel(file)) {
			if (row.getRowNum() == 0) {
				continue;
			}
			PurchaseDto dto = new PurchaseDto();
			dto.setProduct_code(String.valueOf((int) row.getCell(0).getNumericCellValue()));
			dto.setSupplier(row.getCell(1).getStringCellValue());
			dto.setProduct(row.getCell(2).getStringCellValue());
			dto.setQuantity((int) row.getCell(3).getNumericCellValue());
			dto.setPrice((int) row.getCell(4).getNumericCellValue());
			dto.setTotal_price((int) row.getCell(5).getNumericCellValue());
			data.add(dto);
		}
		return ResponseEntity.ok(data);
	}

}