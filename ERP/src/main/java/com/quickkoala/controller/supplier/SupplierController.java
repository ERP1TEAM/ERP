package com.quickkoala.controller.supplier;

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

import com.quickkoala.dto.supplier.SupplierDeliveryDto;
import com.quickkoala.entity.supplier.DeliveryDetailEntity;
import com.quickkoala.entity.supplier.PurchaseEntity;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.supplier.DeliveryDetailService;
import com.quickkoala.service.supplier.PurchaseService;
import com.quickkoala.service.supplier.ViewPurchaseSummaryService;
import com.quickkoala.utils.ExcelUpload;

@Controller
@RequestMapping("supplier")
public class SupplierController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ReceiveTempService receiveTempService;

	@Autowired
	private DeliveryDetailService deliveryDetailService;

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

	// 엑셀 불러오기
	@PostMapping("upload-excel")
	public ResponseEntity<List<SupplierDeliveryDto>> uploadExcel(@RequestParam("excel") MultipartFile file) {
		List<SupplierDeliveryDto> data = new ArrayList<>();
		ExcelUpload eu = new ExcelUpload();
		for (Row row : eu.uploadExcel(file)) {
			if (row.getRowNum() == 0) {
				continue;
			}
			SupplierDeliveryDto dto = new SupplierDeliveryDto();
			dto.setProduct_code(String.valueOf((int) row.getCell(0).getNumericCellValue()));
			dto.setProduct(row.getCell(1).getStringCellValue());
			dto.setQuantity((int) row.getCell(2).getNumericCellValue());
			dto.setPrice((int) row.getCell(3).getNumericCellValue());
			dto.setTotal_price((int) row.getCell(4).getNumericCellValue());
			data.add(dto);
		}
		return ResponseEntity.ok(data);
	}

	// 납품등록
	@GetMapping("delivery_regi")
	public ResponseEntity<String> deliveryRegi(@RequestParam("data") String data, @RequestParam("ea") Integer ea) {
		PurchaseEntity purchaseEntity = purchaseService.getOrderByNumber(data);
		if (purchaseEntity.getQuantity() < ea) {
			return ResponseEntity.ok("over");
		} else {
			DeliveryDetailEntity ent = deliveryDetailService.addDelivery(data, ea);
			receiveTempService.addDelivery(data, ea, ent.getCode());
			return ResponseEntity.ok("ok");
		}
	}

}
