package com.quickkoala.controller.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.supplier.DeliveryDetailEntity;
import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;
import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.supplier.DeliveryDetailService;
import com.quickkoala.service.supplier.ViewDeliveryDetailService;
import com.quickkoala.service.supplier.ViewDeliveryReturnService;
import com.quickkoala.service.supplier.ViewPurchaseSummaryService;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("supplier")
public class SupplierRestController {

	public static final int SIZE = 10;

	@Autowired
	private DeliveryDetailService deliveryDetailService;

	@Autowired
	private ReceiveTempService receiveTempService;

	@Autowired
	private ViewPurchaseSummaryService viewPurchaseSummaryService;

	@Autowired
	private ViewDeliveryDetailService viewDeliveryDetailService;

	@Autowired
	private ViewDeliveryReturnService viewDeliveryReturnService;

	// 발주내역 리스트
	@GetMapping("purchaseData/{pno}")
	public ResponseEntity<Page<ViewPurchaseSummaryEntity>> purchaseData(
			@PathVariable Integer pno,
			@ModelAttribute SearchDto dto, HttpServletRequest request) {
		Page<ViewPurchaseSummaryEntity> result = null;
		result = viewPurchaseSummaryService.getPaginadtedData(pno, SIZE, dto, GetToken.getSupplierCode(request));
		return ResponseEntity.ok(result);
	}

	// 납품등록
	@GetMapping("delivery_regi")
	public ResponseEntity<String> deliveryRegi(
			@RequestParam("data") String data, 
			@RequestParam("ea") Integer ea) {
		try {
			DeliveryDetailEntity ent = deliveryDetailService.addDelivery(data, ea);
			receiveTempService.addDelivery(data, ea, ent.getCode());
			return ResponseEntity.ok("ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		}
	}

	// 납품내역 리스트
	@GetMapping("deliveryData/{pno}")
	public ResponseEntity<Page<ViewDeliveryDetailEntity>> deliveryData(@PathVariable Integer pno,
			@ModelAttribute SearchDto dto, HttpServletRequest request) {
		Page<ViewDeliveryDetailEntity> result = null;
		result = viewDeliveryDetailService.getPaginatedData(pno, SIZE, dto, GetToken.getSupplierCode(request));
		return ResponseEntity.ok(result);
	}

	// 반품내역 리스트
	@GetMapping("returnData/{pno}")
	public ResponseEntity<Page<ViewDeliveryReturnEntity>> returnData(@PathVariable Integer pno,
			@ModelAttribute SearchDto dto, HttpServletRequest request) {
		Page<ViewDeliveryReturnEntity> result = null;
		result = viewDeliveryReturnService.getPaginadtedData(pno, SIZE, dto, GetToken.getSupplierCode(request));
		return ResponseEntity.ok(result);
	}

}
