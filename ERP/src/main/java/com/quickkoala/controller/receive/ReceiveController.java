package com.quickkoala.controller.receive;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.entity.receive.ViewPurchaseEntity;
import com.quickkoala.service.receive.ViewPurchaseService;

@Controller
@RequestMapping("main")
public class ReceiveController {

	@Autowired
	private ViewPurchaseService purchaseViewService;

	// 발주요청 페이지
	@GetMapping("receive/purchaseOrder")
	public String orderPage(Model model) {
		model.addAttribute("items", purchaseViewService.getAllData());
		return "receive/purchaseOrder";
	}

	// 발주내역 페이지
	@GetMapping("receive/purchaseDetail")
	public String listPage() {
		return "receive/purchaseDetail";
	}

	// 가입고 페이지
	@GetMapping("receive/temporaryReceive")
	public String tempPage() {
		return "receive/temporaryReceive";
	}

	// 입고현황 페이지
	@GetMapping("receive/receiveSummary")
	public String receiveSummary() {
		return "receive/receiveSummary";
	}

	// 입고내역 페이지
	@GetMapping("receive/receiveDetail")
	public String receiveDetail() {
		return "receive/receiveDetail";
	}

	// 반품내역 페이지
	@GetMapping("receive/receiveReturn")
	public String receiveReturn() {
		return "receive/receiveReturn";
	}

}