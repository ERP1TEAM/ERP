package com.quickkoala.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quickkoala.entity.TestEntity;
import com.quickkoala.service.TestService;

@Controller
public class Testcontroller {

	@Autowired
	private TestService testService;
	
	 @GetMapping("test")
	 public String index(Model model) {
	    List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5", "Item 5");
	    model.addAttribute("items", items);
	    List<TestEntity> member = testService.getAllMember();
	    model.addAttribute("member",member);
	    return "test";
	 }
	 
}
