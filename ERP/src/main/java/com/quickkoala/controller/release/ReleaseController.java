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
	
	@GetMapping("release/ongoing")
	public String ongoing(Model m) {
		return "release/ongoing";
	}
	
	@GetMapping("release/complete")
	public String complete(Model m) {
		return "release/complete";
	}
	
	@GetMapping("release/cancel")
	public String cancel(Model m) {
		return "release/cancel";
	}
	
	@GetMapping("release/return")
	public String returnMapping(Model m) {
		return "release/return";
	}
}
