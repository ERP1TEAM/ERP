package com.quickkoala.controller.release;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
