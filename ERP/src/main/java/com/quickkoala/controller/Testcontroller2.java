package com.quickkoala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Testcontroller2 {

	@GetMapping("test2")
	public String test() {
		System.out.println("테스트");
		return "test2";
	}
}