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
	
}
