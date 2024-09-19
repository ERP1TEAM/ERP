package com.quickkoala.controller.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.service.stock.CategoryService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class StockRestController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/stock/categories")
    public ResponseEntity<List<CategoryDto>> categoryList() {
    	List<CategoryDto> categoryList=categoryService.getAllOrdersByCode();
    	return ResponseEntity.ok(categoryList);
    }

}
