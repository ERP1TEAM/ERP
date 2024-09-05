package com.quickkoala.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.SupplierDeliveryDto;
import com.quickkoala.entity.PurchaseEntity;
import com.quickkoala.entity.PurchaseProductViewEntity;
import com.quickkoala.service.PurchaseProductViewService;
import com.quickkoala.service.PurchaseService;
import com.quickkoala.service.ReceiveTempService;
import com.quickkoala.utils.ExcelUpload;

@Controller
@RequestMapping("supplier")
public class SupplierController {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Autowired
	private PurchaseProductViewService purchaseProductViewService;
	
	//납품등록 페이지
	@GetMapping("supplierOrderList")
	public String supplierOrderList(Model model) {
		model.addAttribute("items",purchaseProductViewService.getAllOrders());
		return "supplier/supplierOrderList";
	}
	
	//엑셀 불러오기
	@PostMapping("upload-excel")
	public ResponseEntity<List<SupplierDeliveryDto>> uploadExcel(@RequestParam("excel") MultipartFile file) {
		List<SupplierDeliveryDto> data = new ArrayList<>();
		ExcelUpload eu = new ExcelUpload(); 
		System.out.println(file.getOriginalFilename());
		for (Row row : eu.uploadExcel(file)) {
	        if (row.getRowNum() == 0) {
	            continue;
	        }
	        SupplierDeliveryDto dto = new SupplierDeliveryDto();
	        dto.setProduct_code(String.valueOf((int)row.getCell(0).getNumericCellValue()));
	        dto.setProduct(row.getCell(1).getStringCellValue());
	        dto.setQuantity((int)row.getCell(2).getNumericCellValue());
	        dto.setPrice((int)row.getCell(3).getNumericCellValue());
	        dto.setTotal_price((int)row.getCell(4).getNumericCellValue());
	        data.add(dto);
	    }
		return ResponseEntity.ok(data);
	}
	
	//납품등록
	@GetMapping("delivery_regi")
	public ResponseEntity<String> deliveryRegi(@RequestParam("data") String data, @RequestParam("ea") Integer ea){
		PurchaseEntity purchaseEntity = purchaseService.getOrderByNumber(data);
		System.out.println(purchaseEntity.getQuantity());
		if(purchaseEntity.getQuantity() < ea) {
			return ResponseEntity.ok("over");			
		}else {
			receiveTempService.addDelivery(data, ea);
			return ResponseEntity.ok("ok");
		}
	}
}
