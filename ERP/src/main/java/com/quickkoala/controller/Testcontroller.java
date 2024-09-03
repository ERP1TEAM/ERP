package com.quickkoala.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	 
	
	 
	 @PostMapping("/upload-excel")
	 public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
		 List<TestEntity> members = new ArrayList<>();
		 
		 try (InputStream inputStream = file.getInputStream()) {
		     Workbook workbook = WorkbookFactory.create(inputStream);
		     Sheet sheet = workbook.getSheetAt(0);
		     for (Row row : sheet) {
		         if (row.getRowNum() == 0) {
		             continue; // Skip header row
		         }
		         /*
	          Member member = new Member();
		         member.setFirstName(row.getCell(0).getStringCellValue());
		         member.setLastName(row.getCell(1).getStringCellValue());
		         member.setEmail(row.getCell(2).getStringCellValue());
		         members.add(member);*/
		     }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		
		 model.addAttribute("members", members);
		 return "display";
	 }

	 
}
