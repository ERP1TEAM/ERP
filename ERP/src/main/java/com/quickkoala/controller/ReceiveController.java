package com.quickkoala.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.PurchaseDto;
import com.quickkoala.dto.PurchaseListDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.service.PurchaseService;

@Controller
@RequestMapping("receive")
public class ReceiveController {
	
	@Autowired
	private PurchaseService purchaseService;
	
	//발주요청 페이지
	@GetMapping("purchaseOrder")
	public String tablePage() {
		return "receive/purchaseOrder";
	}
	
	//발주요청 페이지
	@GetMapping("purchaseOrder3")
	public String orderPage() {
		return "receive/purchaseOrder3";
	}
	
	//발주내역 페이지
	@GetMapping("purchaseOrderList")
	public String listPage(Model model) {
		List<PurchaseEntity> item = purchaseService.getAllOrders();
		model.addAttribute("items",item);
		return "receive/purchaseOrderList";
	}
	
	//입고현황 페이지
	@GetMapping("temporaryReceive")
	public String tempPage(Model model, String status) {
		List<PurchaseEntity> item = purchaseService.getAllOrdersByStatus(status);
		model.addAttribute("items",item);
		return "receive/temporaryReceive";
	}
	
	//발주요청
	@PostMapping("purchaseAdd")
	public ResponseEntity<String> purchaseAdd(@ModelAttribute PurchaseListDto orders){
		System.out.println(orders.getProduct_code());
		purchaseService.addOrder(orders);
		return ResponseEntity.ok("success");
	}
	
	//엑셀 불러오기
	@PostMapping("upload-excel")
	public ResponseEntity<List<PurchaseDto>> uploadExcel(@RequestParam("excel") MultipartFile file) {
		List<PurchaseDto> data = new ArrayList<>();
		 
		try (InputStream inputStream = file.getInputStream()) {
		    Workbook workbook = WorkbookFactory.create(inputStream);
		    Sheet sheet = workbook.getSheetAt(0);
		    for (Row row : sheet) {
		    	
		        if (row.getRowNum() == 0) {
		            continue;
		        }
		        PurchaseDto dto = new PurchaseDto();
		        dto.setProduct_code(String.valueOf((int)row.getCell(0).getNumericCellValue()));
		        dto.setSupplier(row.getCell(1).getStringCellValue());
		        dto.setProduct(row.getCell(2).getStringCellValue());
		        dto.setQuantity((int)row.getCell(3).getNumericCellValue());
		        dto.setPrice((int)row.getCell(4).getNumericCellValue());
		        dto.setTotal_price((int)row.getCell(5).getNumericCellValue());
		        data.add(dto);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(data);
	 }
	
}