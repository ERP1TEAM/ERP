package com.quickkoala.controller.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.supplier.ViewDeliveryDetailEntity;
import com.quickkoala.entity.supplier.ViewDeliveryReturnEntity;
import com.quickkoala.entity.supplier.ViewPurchaseSummaryEntity;
import com.quickkoala.service.supplier.ViewDeliveryDetailService;
import com.quickkoala.service.supplier.ViewDeliveryReturnService;
import com.quickkoala.service.supplier.ViewPurchaseSummaryService;

@RestController
@RequestMapping("supplier")
public class SupplierRestController {

	@Autowired
	private ViewPurchaseSummaryService viewPurchaseSummaryService;
	
	@Autowired
	private ViewDeliveryDetailService viewDeliveryDetailService;

	@Autowired
	private ViewDeliveryReturnService viewDeliveryReturnService;

	// 발주내역 리스트
	@GetMapping("purchaseData/{pno}")
	public Page<ViewPurchaseSummaryEntity> purchaseData(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewPurchaseSummaryEntity> result = null;
		int size = 5;
		if (code.equals("") || word.equals("")) {
			result = viewPurchaseSummaryService.getPaginadtedData(pno, size);
		} else {
			result = viewPurchaseSummaryService.getPaginadtedData(pno, size, code, word);
		}
		return result;
	}
	
	// 납품내역 리스트
	@GetMapping("deliveryData/{pno}")
	public Page<ViewDeliveryDetailEntity> deliveryData(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewDeliveryDetailEntity> result = null;
		int size = 5;
		if (code.equals("") || word.equals("")) {
			result = viewDeliveryDetailService.getPaginatedData(pno, size);
		} else {
			result = viewDeliveryDetailService.getPaginatedData(pno, size, code, word);
		}
		return result;
	}

	// 반품내역 리스트
	@GetMapping("returnData/{pno}")
	public Page<ViewDeliveryReturnEntity> returnData(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewDeliveryReturnEntity> result = null;
		int size = 5;
		if (code.equals("") || word.equals("")) {
			result = viewDeliveryReturnService.getPaginadtedData(pno, size);
		} else {
			result = viewDeliveryReturnService.getPaginadtedData(pno, size, code, word);
		}
		return result;
	}
}
