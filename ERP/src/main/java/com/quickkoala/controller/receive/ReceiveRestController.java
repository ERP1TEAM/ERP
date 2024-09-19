package com.quickkoala.controller.receive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.receive.ReceiveDetailEntity;
import com.quickkoala.entity.receive.ReceiveReturnEntity;
import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;
import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.receive.ViewReceiveTempEntity;
import com.quickkoala.service.LotService;
import com.quickkoala.service.receive.ReceiveDetailService;
import com.quickkoala.service.receive.ReceiveReturnService;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.receive.ViewPurchaseDetailService;
import com.quickkoala.service.receive.ViewReceiveReturnService;
import com.quickkoala.service.receive.ViewReceiveService;
import com.quickkoala.service.receive.ViewReceiveSummaryService;
import com.quickkoala.service.receive.ViewReceiveTempService;

@RestController
@RequestMapping("main")
public class ReceiveRestController {
	
	private final int SIZE = 5;
	
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
	public Page<ViewPurchaseDetailEntity> purchaseData(@PathVariable Integer pno, @PathVariable String status, @RequestParam String code, @RequestParam String word){
		
		Page<ViewPurchaseDetailEntity> result = null;
		if(status.equals("all")) {
			if(code.equals("") || word.equals("")) {
				result = viewPurchaseDetailService.getPaginatedData(pno, SIZE);
			}else {
				result = viewPurchaseDetailService.getPaginatedData(pno, SIZE, code, word);				
			}
		}else {
			if(code.equals("") || word.equals("")) {
				result = viewPurchaseDetailService.getPaginatedDataByStatus(status, pno, SIZE);																
			}else {
				result = viewPurchaseDetailService.getPaginatedData(pno, SIZE, code, word);
			}
		}
		return result; 
	}
	
	// 가입고 페이지 데이터
	@GetMapping("receive/tempReceiveData/{pno}")
	public Page<ViewReceiveTempEntity> tempReceiveData(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word) {
		Page<ViewReceiveTempEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = viewReceiveTempService.getPaginatedData(pno, SIZE);
		}else {
			result = viewReceiveTempService.getPaginatedData(pno, SIZE, code, word);
		}
		return result;
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
	public Page<ViewReceiveSummaryEntity> summaryData(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word) {
		Page<ViewReceiveSummaryEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = viewReceiveSummaryService.getPaginatedData(pno, SIZE); 
		}else {
			result = viewReceiveSummaryService.getPaginatedData(pno, SIZE, code, word);
		}
		return result;
	}
	
	// 입고내역 데이터 + 페이징
	@GetMapping("receive/detailData/{pno}")
	public Page<ViewReceiveEntity> detailData(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word) {
		Page<ViewReceiveEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = viewReceiveService.getPaginatedData(pno, SIZE); 
		}else {
			result = viewReceiveService.getPaginatedData(pno, SIZE, code, word);
		}
		return result;
	}
	
	// 입고반품 데이터 + 페이징
	@GetMapping("receive/returnData/{pno}")
	public Page<ViewReceiveReturnEntity> returnData(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word) {
		Page<ViewReceiveReturnEntity> result = null;
		if(code.equals("") || word.equals("")) {
			result = viewReceiveReturnService.getPaginatedData(pno, SIZE);
		}else {
			result = viewReceiveReturnService.getPaginatedData(pno, SIZE, code, word);
		}
		return result;
	}

}
