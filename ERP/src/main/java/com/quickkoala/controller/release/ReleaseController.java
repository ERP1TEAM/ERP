package com.quickkoala.controller.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


@Controller
@RequestMapping("main")
public class ReleaseController {
	
	@Autowired
	private OrderReleaseService orderReleaseService;
	
	@Autowired
	private ReleaseCancelService releaseCancelService;
	
	@Autowired
	private ReleaseCompleteService releaseCompleteService;
	
	@Autowired
	private ReleaseProductsService releaseProductsService;
	
	@Autowired
	private ReleaseReturnProductsService releaseReturnPrdouctsService;
	
	@Autowired
	private ViewReleaseOngoingService viewReleaseOngoingService;
	
	@Autowired
	private ViewReleaseCancelService viewReleaseCancelService;
	
	@Autowired
	private ViewReleaseCompleteService viewReleaseCompleteService;
	
	@Autowired
	private ViewReleaseProductsService viewReleaseProductsService;
	
	@Autowired
	private ViewReleaseReturnProductsService viewReleaseReturnPrdouctsService;
	
	/*
	 * ongoing
	 */
	
	@GetMapping("release/ongoing")
	public String ongoing(Model m) {
		//m.addAttribute("list",this.viewReleaseOngoingService.getAll());
		return "release/ongoing";
	}
	
	
	
	
	
	/*
	 * complete
	 */
	@GetMapping("release/complete")
	public String complete(Model m) {
		//m.addAttribute("list",this.viewReleaseCompleteService.getAll());
		return "release/complete";
	}
	
	
	
	
	
	/*
	 * cancel
	 */
	@GetMapping("release/cancel")
	public String cancel(Model m) {
		//m.addAttribute("list",this.viewReleaseCancelService.getAll());
		return "release/cancel";
	}
	
	
	
	
	
	/*
	 * refund
	 */
	@GetMapping("release/return")
	public String returnMapping(Model m) {
		//m.addAttribute("list",this.viewReleaseReturnPrdouctsService.getAll());
		return "release/return";
	}
}
