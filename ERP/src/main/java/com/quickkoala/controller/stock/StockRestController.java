package com.quickkoala.controller.stock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.stock.CategoryDto;
import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.dto.stock.ProductDto;
import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.entity.stock.CategoryEntity;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.entity.stock.ViewProductStockEntity;
import com.quickkoala.service.client.SupplierService;
import com.quickkoala.service.stock.CategoryService;
import com.quickkoala.service.stock.LocationService;
import com.quickkoala.service.stock.ProductService;
import com.quickkoala.service.stock.ViewProductStockService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class StockRestController {
	private final int SIZE = 5;

	//***** 재고 등록 부분 *****//
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;
	
	   
	 @PostMapping("/stock/inventories")
	 public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto,@RequestParam String manager) {
		 if (manager == null || manager.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 매니저가 없을 때 400 에러 반환
		 }
		 System.out.println(manager);
		 try {
	        ProductEntity saveproduct = productService.saveProduct(productDto, manager);
	        ProductDto saveproductDto = productService.convertToProductDto(saveproduct);
	        return ResponseEntity.ok(saveproductDto);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	 }
	 
	 //옵션
	 @GetMapping("/stock/inventoryselectoptions")
	 public ResponseEntity<Map<String, Object>> getAllOptions() {
        List<LocationDto> locationOption = locationService.getAllOrdersByCode();
        List<CategoryDto> categoryOption = categoryService.getAllOrdersByCode();
        List<SupplierEntity> supplierOption = supplierService.getAllData();
        
        Map<String, Object> response = new HashMap<>();
        response.put("locations", locationOption);
        response.put("categories", categoryOption);
        response.put("suppliers", supplierOption);

	    return ResponseEntity.ok(response);
	    }
	
	 //난수 생성 + 중복 체크
	 @GetMapping("/stock/inventoryrandomcode")
	 public ResponseEntity<String> inventoryrandomcode(){
		 try {
		 String randomcode= productService.checkduplicateCode();
		 return ResponseEntity.ok(randomcode);
		 }catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.CONFLICT).body("상품코드가 중복됐습니다.");
		 }
	 }
	 
	 
	//*****재고 리스트 부분*****//
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
	
	@GetMapping("/stock/category/{categoryCode}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryCode) {
	     CategoryDto categoryDto = categoryService.getCategoryByCode(categoryCode);
	     if (categoryDto != null) {
	    	 return ResponseEntity.ok(categoryDto);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     }
	}
	     
    @PutMapping("/stock/category/{categoryCode}")
    public ResponseEntity<String> updateCategory(@PathVariable String categoryCode,@RequestBody CategoryDto categoryDto){
    	boolean updateCategory = categoryService.updateCategory(categoryCode, categoryDto);
    	
    	if(updateCategory) {
    		return ResponseEntity.ok("카테고리가 수정 되었습니다.");
    	}else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리 코드를 찾을 수 없습니다.");
    	}
	}
    
    @DeleteMapping("/stock/category/{categoryCodes}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable String categoryCodes) {
    	List<String> categoryCodeList = Arrays.asList(categoryCodes.split(","));
        Map<String, Object> categoryresponse = categoryService.deleteCategory(categoryCodeList);
        
        categoryresponse.put("ok", true);
        
        return ResponseEntity.ok(categoryresponse);
    }
}
