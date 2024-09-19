package com.quickkoala.controller.release;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.dto.release.ReleaseCompleteDto;
import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.dto.release.ReleaseReturnDto;
import com.quickkoala.service.release.OrderReleaseService;
import com.quickkoala.service.release.ReleaseCancelService;
import com.quickkoala.service.release.ReleaseCompleteService;
import com.quickkoala.service.release.ReleaseProductsService;
import com.quickkoala.service.release.ReleaseReturnProductsService;
import com.quickkoala.service.release.ViewReleaseCancelService;
import com.quickkoala.service.release.ViewReleaseCompleteService;
import com.quickkoala.service.release.ViewReleaseOngoingService;
import com.quickkoala.service.release.ViewReleaseProductsService;
import com.quickkoala.service.release.ViewReleaseReturnProductsService;

@RestController
@RequestMapping("main")
public class ReleaseRestController {
	
	@Autowired
	private OrderReleaseService orderReleaseService;
	
	@Autowired
	private ReleaseCancelService releaseCancelService;
	
	@Autowired
	private ReleaseCompleteService releaseCompleteService;
	
	@Autowired
	private ReleaseProductsService releaseProductsService;
	
	@Autowired
	private ReleaseReturnProductsService releaseRefundPrdouctsService;
	
	@Autowired
	private ViewReleaseOngoingService viewReleaseOngoingService;
	
	@Autowired
	private ViewReleaseCancelService viewReleaseCancelService;
	
	@Autowired
	private ViewReleaseCompleteService viewReleaseCompleteService;
	
	@Autowired
	private ViewReleaseProductsService viewReleaseProductsService;
	
	@Autowired
	private ViewReleaseReturnProductsService viewReleaseRefundPrdouctsService;
	
	@GetMapping("release/test")
	public String test() {
		orderReleaseService.getMaxNumber();
		return null;
	}
	
	private final int size=5;
	
	@PostMapping("release/paging")
	public List<ReleaseOngoingDto> paging(@RequestParam int pg) {
		return viewReleaseOngoingService.getAll(pg,size);
	}
	
	@PostMapping("release/cancelpaging")
	public List<ReleaseCancelDto> cancelpaging(@RequestParam int pg) {
		return viewReleaseCancelService.getAll(pg,size);
	}
	
	@PostMapping("release/completepaging")
	public List<ReleaseCompleteDto> completepaging(@RequestParam int pg) {
		return (List<ReleaseCompleteDto>) viewReleaseCompleteService.getAll(pg,size);
	}
	
	@PostMapping("release/refundpaging")
	public List<ReleaseReturnDto> refundpaging(@RequestParam int pg) {
		return viewReleaseRefundPrdouctsService.getAll(pg,size);
	}
	
	@PostMapping("release/cancel.do")
	public String cancel(@RequestParam String id) {
		return orderReleaseService.saveStatus(id,"출고취소");
	}
	
	@PostMapping("release/complete.do")
	public String complete(@RequestParam String id) {
		return orderReleaseService.saveStatus(id,"출고완료");
	}
	
	@PostMapping("release/postpone.do")
	public String postpone(@RequestParam String id) {
		return orderReleaseService.saveStatus(id,"출고지연");
	}

	
	
	
}
