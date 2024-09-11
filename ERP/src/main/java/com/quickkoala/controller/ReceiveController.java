package com.quickkoala.controller;

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

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.dto.ReceiveModalDto;
import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.PurchaseDetailViewEntity;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.ReceiveDetailEntity;
import com.quickkoala.entity.ReceiveReturnEntity;
import com.quickkoala.entity.ReceiveTempEntity;
import com.quickkoala.entity.SupplierEntity;
import com.quickkoala.entity.ReceiveTempViewEntity;
import com.quickkoala.service.PurchaseViewService;
import com.quickkoala.service.PurchaseDetailViewService;
import com.quickkoala.service.PurchaseService;
import com.quickkoala.service.ReceiveDetailService;
import com.quickkoala.service.ReceiveReturnService;
import com.quickkoala.service.ReceiveSummaryViewService;
import com.quickkoala.service.ReceiveTempService;
import com.quickkoala.service.ReceiveViewService;
import com.quickkoala.service.SupplierService;
import com.quickkoala.service.ReceiveTempViewService;
import com.quickkoala.utils.ExcelUpload;

@Controller
@RequestMapping("receive")
public class ReceiveController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ReceiveTempViewService temporaryReceiveViewService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private PurchaseViewService purchaseViewService;

	@Autowired
	private ReceiveDetailService receiveDetailService;
	
	@Autowired
	private ReceiveReturnService receiveReturnService;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Autowired
	private ReceiveSummaryViewService receiveSummaryViewService;
	
	@Autowired
	private ReceiveViewService receiveViewService;
	
	@Autowired
	private PurchaseDetailViewService purchaseDetailViewService;

	// 발주요청 페이지
	@GetMapping("purchaseOrder")
	public String orderPage(Model model) {
		model.addAttribute("items", purchaseViewService.getAllData());
		return "receive/purchaseOrder";
	}
	
	// 발주내역 페이지
	@GetMapping("purchaseOrderList")
	public String listPage(Model model, String status) {
		List<PurchaseDetailViewEntity> item = purchaseDetailViewService.getData();
		model.addAttribute("items", item);
		return "receive/purchaseOrderList";
	}

	// 가입고 페이지
	@GetMapping("temporaryReceive")
	public String tempPage(Model model, String status) {
		List<ReceiveTempViewEntity> item = temporaryReceiveViewService.getAllOrders(status);
		model.addAttribute("items", item);
		return "receive/temporaryReceive";
	}
	
	// 입고현황(진) 페이지
	@GetMapping("receiveSummary")
	public String receiveSummary(Model model) {
		model.addAttribute("items",receiveSummaryViewService.getData());
		return "receive/receiveSummary";
	}

	// 입고내역 페이지
	@GetMapping("receiveDetail")
	public String receiveDetail(Model model) {
		model.addAttribute("items", receiveViewService.getData());
		return "receive/receiveDetail";
	}

	// 발주요청
	@PostMapping("purchaseAdd")
	public ResponseEntity<String> purchaseAdd(@ModelAttribute PurchaseListDto orders) {
		purchaseService.addOrders(orders);
		return ResponseEntity.ok("success");
	}

	// 제조사 자동완성
	@GetMapping("autocomplete")
	public ResponseEntity<List<SupplierEntity>> autocomplete(@RequestParam String term) {
		return ResponseEntity.ok(supplierService.searchByName(term));
	}

	// 엑셀 불러오기
	@PostMapping("upload-excel")
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

	// 입고
	@PostMapping("receiving")
	public ResponseEntity<String> receiving(@ModelAttribute ReceivingDto dto) {
		ReceiveDetailEntity result = receiveDetailService.addData(dto.getCode(), dto.getReQty());
		ReceiveReturnEntity result2 = receiveReturnService.addData(dto);
		if (result == null || result2 == null) {
			return ResponseEntity.ok("no");
		} else {
			receiveTempService.modifyWtquantity(dto.getCode(), 0);
			return ResponseEntity.ok("ok");
		}
	}

	// 입고확정 모달
	@GetMapping("receivingModal")
	public ResponseEntity<ReceiveModalDto> receivingModal(
			@RequestParam("ornum") String ornum,
			@RequestParam("code") String code,
			@RequestParam("name") String name, @RequestParam("qty") Integer qty, @RequestParam("wqty") Integer wqty) {
		ReceiveModalDto dto = new ReceiveModalDto();
		dto.setOrnum(ornum);
		dto.setCode(code);
		dto.setName(name);
		dto.setQty(qty);
		dto.setWqty(wqty);
		return ResponseEntity.ok(dto);
	}
}