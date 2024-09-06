package com.quickkoala.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	@GetMapping("login.do")
	public String test() {
		return "/member/login";
	}
	@GetMapping("main.do")
	public String getMethodName() {
		return "main";
	}
	@GetMapping("register.do")
	public String register() {
		return "/member/register";
	}
	@GetMapping("/distribution/distribution.do")
	public String test1() {
		return "test1";
	}
	@GetMapping("/supplying/supplying.do")
	public String test2() {
		return "test2";
	}
	@GetMapping("/sales/sales.do")
	public String test3() {
		return "test3";
	}
	
}
