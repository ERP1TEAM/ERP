package com.quickkoala.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.ViewPurchaseDetailEntity;
import com.quickkoala.entity.ReceiveDetailEntity;
import com.quickkoala.entity.ReceiveReturnEntity;
import com.quickkoala.entity.ViewReceiveTempEntity;
import com.quickkoala.entity.ViewReceiveEntity;
import com.quickkoala.entity.ViewReceiveReturnEntity;
import com.quickkoala.entity.ViewReceiveSummaryEntity;
import com.quickkoala.service.LotService;
import com.quickkoala.service.ReceiveDetailService;
import com.quickkoala.service.ReceiveReturnService;
import com.quickkoala.service.ReceiveTempService;
import com.quickkoala.service.ViewReceiveTempService;
import com.quickkoala.service.ViewPurchaseDetailService;
import com.quickkoala.service.ViewReceiveReturnService;
import com.quickkoala.service.ViewReceiveService;
import com.quickkoala.service.ViewReceiveSummaryService;

@RestController
@RequestMapping("main")
public class ReceiveRestController {

	@Autowired
	private ViewReceiveTempService viewReceiveTempService;
	
	@Autowired
	private ReceiveDetailService receiveDetailService;
	
	@Autowired
	private ReceiveReturnService receiveReturnService;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private ViewPurchaseDetailService viewPurchaseDetailService;

	@Autowired
	private ViewReceiveService viewReceiveService;
	
	@Autowired
	private ViewReceiveReturnService viewReceiveReturnService;
	
	@Autowired
	private ViewReceiveSummaryService viewReceiveSummaryService;
	
	// 발주내역 페이지 데이터
	@GetMapping("receive/purchaseData/{pno}/{status}")
	public Page<ViewPurchaseDetailEntity> purchaseData(@PathVariable Integer pno, @PathVariable String status){
		int size = 2;
		Page<ViewPurchaseDetailEntity> items = null;
		if(status.equals("all")) {
			items = viewPurchaseDetailService.getPaginatedData(pno, size);
		}else {
			items = viewPurchaseDetailService.getPaginatedDataByStatus(status, pno, size);
		}
		return items; 
	}
	
	// 가입고 페이지 데이터
	@GetMapping("receive/temporaryData/{status}")
	public List<ViewReceiveTempEntity> tempData(@PathVariable String status) {
		return viewReceiveTempService.getAllOrders(status);
	}
	
	// 가입고 페이지 데이터
	@GetMapping("receive/tempReceiveData/{pno}")
	public Page<ViewReceiveTempEntity> tempReceiveData(@PathVariable Integer pno) {
		System.out.println(pno);
		int size=2;
		return viewReceiveTempService.getPaginatedData(pno, size);
	}

	// 입고확정
	@PostMapping("receive/receiving")
	public String receiving(@ModelAttribute ReceivingDto dto) {
		ReceiveDetailEntity result = new ReceiveDetailEntity();
		ReceiveReturnEntity result2 = new ReceiveReturnEntity();
		
		if(dto.getReQty() != 0) {
			result = receiveDetailService.addData(dto.getCode(), dto.getReQty());
			lotService.addLot(dto);
		}
		if(dto.getCaQty() != 0) {
			result2 = receiveReturnService.addData(dto);			
		}
		
		if (result == null || result2 == null) {
			return "no";
		} else {
			receiveTempService.removeData(dto.getCode());
			return "ok";
		}
	}
	
	// 입고현황 데이터 + 페이징
	@GetMapping("receive/summaryData/{pno}")
	public Page<ViewReceiveSummaryEntity> summaryData(@PathVariable Integer pno) {
		int size = 3;
		return viewReceiveSummaryService.getPaginatedData(pno, size);
	}
	
	// 입고내역 데이터 + 페이징
	@GetMapping("receive/detailData/{pno}")
	public Page<ViewReceiveEntity> detailData(@PathVariable Integer pno) {
		int size = 5;
		return viewReceiveService.getPaginatedData(pno, size);
	}
	
	// 입고반품 데이터 + 페이징
	@GetMapping("receive/returnData/{pno}")
	public Page<ViewReceiveReturnEntity> returnData(@PathVariable Integer pno) {
		int size = 2;
		return viewReceiveReturnService.getPaginatedData(pno, size);
	}

}
