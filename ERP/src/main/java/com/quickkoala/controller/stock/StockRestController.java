package com.quickkoala.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.entity.stock.CategoryEntity;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.ViewProductStockEntity;
import com.quickkoala.service.stock.CategoryService;
import com.quickkoala.service.stock.ViewProductStockService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class StockRestController {
	private final int SIZE = 5;

	//*****재고목록 부분*****//
	@Autowired
	private ViewProductStockService viewProductStockService;
	
	@GetMapping("/stock/viewproductstocks/{pno}")
	public Page<ViewProductStockEntity> viewproductstockList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewProductStockEntity> result = viewProductStockService.getPaginatedData(pno, SIZE);
    	if (code.equals("") || word.equals("")) {
			result = viewProductStockService.getPaginatedData(pno, SIZE);
		} else {
			result = viewProductStockService.getPaginatedData(pno, SIZE, code, word);
		}
    	return result;
		
	}
	
	//******카테고리 모달 부분*****//
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/stock/categories/{pno}")
	public Page<CategoryEntity> categoryList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		
    	Page<CategoryEntity> result = categoryService.getPaginatedData(pno, SIZE);
    	if (code.equals("") || word.equals("")) {
			result = categoryService.getPaginatedData(pno, SIZE);
		} else {
			result = categoryService.getPaginatedData(pno, SIZE, code, word);
		}
    	return result;
	}
	
	@PostMapping("/stock/categories")
	public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto){
		CategoryEntity categorysave = categoryService.saveCategory(categoryDto);
		if(categorysave == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 카테고리 코드 입니다.");
		}
		return ResponseEntity.ok("카테고리가 등록되었습니다.");
	}
	
}
