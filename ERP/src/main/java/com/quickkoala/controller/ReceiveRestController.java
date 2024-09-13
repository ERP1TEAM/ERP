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
import com.quickkoala.entity.ReceiveTempViewEntity;
import com.quickkoala.entity.ViewReceiveEntity;
import com.quickkoala.repository.ViewPurchaseDetailRepository;
import com.quickkoala.service.LotService;
import com.quickkoala.service.ReceiveDetailService;
import com.quickkoala.service.ReceiveReturnService;
import com.quickkoala.service.ReceiveTempService;
import com.quickkoala.service.ReceiveTempViewService;
import com.quickkoala.service.ViewPurchaseDetailService;
import com.quickkoala.service.ViewReceiveService;

@RestController
@RequestMapping("main")
public class ReceiveRestController {

	@Autowired
	private ReceiveTempViewService receiveTempViewService;
	
	@Autowired
	private ReceiveDetailService receiveDetailService;
	
	@Autowired
	private ReceiveReturnService receiveReturnService;
	
	@Autowired
	private ReceiveTempService receiveTempService;
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private ViewPurchaseDetailRepository purchaseDetailViewRepository;
	
	@Autowired
	private ViewPurchaseDetailService viewPurchaseDetailService;

	@Autowired
	private ViewReceiveService viewReceiveService;
	
	// 발주내역 페이지 데이터
	@GetMapping("receive/purchaseData/{pno}/{status}")
	public Page<ViewPurchaseDetailEntity> purchaseData(@PathVariable Integer pno, @PathVariable String status){
		int size = 5;
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
	public List<ReceiveTempViewEntity> tempData(@PathVariable String status) {
		return receiveTempViewService.getAllOrders(status);
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
	
	// 입고내역 데이터 + 페이징
	@GetMapping("receive/detailData/{pno}")
	public Page<ViewReceiveEntity> detailData(@PathVariable Integer pno) {
		int size = 10;
		return viewReceiveService.getPaginatedData(pno, size);
	}

}
