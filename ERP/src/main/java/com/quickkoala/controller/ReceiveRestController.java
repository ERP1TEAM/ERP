package com.quickkoala.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.ReceiveDetailEntity;
import com.quickkoala.entity.ReceiveReturnEntity;
import com.quickkoala.entity.ReceiveTempViewEntity;
import com.quickkoala.service.LotService;
import com.quickkoala.service.ReceiveDetailService;
import com.quickkoala.service.ReceiveReturnService;
import com.quickkoala.service.ReceiveTempService;
import com.quickkoala.service.ReceiveTempViewService;

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

	// 가입고 페이지 데이터
	@GetMapping("receive/temporaryData/{status}")
	public List<ReceiveTempViewEntity> tempData(@PathVariable String status) {
		return receiveTempViewService.getAllOrders(status);
	}

	// 입고확정
	@PostMapping("receive/receiving")
	public String receiving(@ModelAttribute ReceivingDto dto) {
		ReceiveDetailEntity result = receiveDetailService.addData(dto.getCode(), dto.getReQty());
		ReceiveReturnEntity result2 = receiveReturnService.addData(dto);
		if (result == null || result2 == null) {
			return "no";
		} else {
			receiveTempService.removeData(dto.getCode());
			lotService.addLot(dto);
			return "ok";
		}
	}

}
