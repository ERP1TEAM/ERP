package com.quickkoala.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	@GetMapping("login.do")
	public String test() {
		return "/member/login";
	}
	@GetMapping("sidebar")
	public String sidevar() {
		return "sidebar";
	}
	@GetMapping("main.do")
	public String getMethodName() {
		return "main";
	}
	
}
