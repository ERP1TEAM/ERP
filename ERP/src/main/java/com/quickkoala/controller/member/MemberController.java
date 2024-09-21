package com.quickkoala.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MemberController {
	

    
	@GetMapping("/login")
	public String test() {
		return "member/login";
	}
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}
	@GetMapping("/main/home")
	public String test1() {
		return "sales/mainHome";
	}
	@GetMapping("/supplier/home")
	public String test2() {
		return "sales/supplierHome";
	}
	
}
