package com.quickkoala.controller.supplier;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.dto.supplier.DeliveryRegiDto;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("supplier")
public class SupplierRestController {

	public static final int SIZE = 10;

	private final DeliveryDetailService deliveryDetailService;
    private final ReceiveTempService receiveTempService;
    private final ViewPurchaseSummaryService viewPurchaseSummaryService;
    private final ViewDeliveryDetailService viewDeliveryDetailService;
    private final ViewDeliveryReturnService viewDeliveryReturnService;

    public SupplierRestController(DeliveryDetailService deliveryDetailService,
                                  ReceiveTempService receiveTempService,
                                  ViewPurchaseSummaryService viewPurchaseSummaryService,
                                  ViewDeliveryDetailService viewDeliveryDetailService,
                                  ViewDeliveryReturnService viewDeliveryReturnService) {
        this.deliveryDetailService = deliveryDetailService;
        this.receiveTempService = receiveTempService;
        this.viewPurchaseSummaryService = viewPurchaseSummaryService;
        this.viewDeliveryDetailService = viewDeliveryDetailService;
        this.viewDeliveryReturnService = viewDeliveryReturnService;
    }

	// 발주내역 리스트
	@GetMapping("purchaseData/{pno}")
	public ResponseEntity<Page<ViewPurchaseSummaryEntity>> purchaseData(
			@PathVariable Integer pno,
			@ModelAttribute SearchDto dto, HttpServletRequest request) {
		Page<ViewPurchaseSummaryEntity> result = viewPurchaseSummaryService.getPaginatedData(pno, SIZE, dto, getSupplierCode(request));
		return ResponseEntity.ok(result);
	}

	// 납품등록
	@PostMapping("delivery_regi")
	@Transactional
	public ResponseEntity<String> deliveryRegi(@RequestBody @Valid DeliveryRegiDto dto) {
	    try {
	        DeliveryDetailEntity ent = deliveryDetailService.addDelivery(dto.getData(), dto.getEa());
	        if (ent == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DeliveryDetailEntity is null");
	        }
	        
	        receiveTempService.addDelivery(dto.getData(), dto.getEa(), ent.getCode());
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
		Page<ViewDeliveryDetailEntity> result = viewDeliveryDetailService.getPaginatedData(pno, SIZE, dto, getSupplierCode(request));
		return ResponseEntity.ok(result);
	}

	// 반품내역 리스트
	@GetMapping("returnData/{pno}")
	public ResponseEntity<Page<ViewDeliveryReturnEntity>> returnData(@PathVariable Integer pno,
			@ModelAttribute SearchDto dto, HttpServletRequest request) {
		Page<ViewDeliveryReturnEntity> result = viewDeliveryReturnService.getPaginatedData(pno, SIZE, dto, getSupplierCode(request));
		return ResponseEntity.ok(result);
	}
	
	// 발주처 코드
	private String getSupplierCode(HttpServletRequest request) {
	    return GetToken.getSupplierCode(request);
	}

}
