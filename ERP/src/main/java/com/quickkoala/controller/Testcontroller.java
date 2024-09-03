package com.quickkoala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Testcontroller {

	@GetMapping("test")
	public String test() {
		
		return "test";
	}
}
