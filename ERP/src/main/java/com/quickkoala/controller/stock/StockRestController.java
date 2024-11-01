package com.quickkoala.controller.stock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import com.quickkoala.dto.stock.ProductDto;
import com.quickkoala.dto.stock.ViewProductStockSupplierDto;
import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.entity.stock.CategoryEntity;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.entity.stock.ViewDailyStockSummaryEntity;
import com.quickkoala.entity.stock.ViewProductStockSupplierEntity;
import com.quickkoala.service.client.SupplierService;
import com.quickkoala.service.stock.CategoryService;
import com.quickkoala.service.stock.ProductService;
import com.quickkoala.service.stock.StockService;
import com.quickkoala.service.stock.ViewDailyStockSummaryService;
import com.quickkoala.service.stock.ViewProductStockSupplierService;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class StockRestController {
	private final int SIZE = 15;
	private final int modalSIZE = 5;

	//***** 재고 *****//
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private ViewDailyStockSummaryService viewDailyStockSummaryService;
	
	@Autowired
	private ViewProductStockSupplierService viewProductStockSupplierService;
	
	@GetMapping("/stock/stocklist/{pno}")
	public ResponseEntity<Page<ViewProductStockSupplierEntity>> getPaginatedStockList(
	        @PathVariable int pno,
	        @RequestParam String code,
	        @RequestParam String word) {
	    
	    Page<ViewProductStockSupplierEntity> paginatedData = viewProductStockSupplierService.getPaginatedData(pno, SIZE, code, word);
	    return ResponseEntity.ok().body(paginatedData);
	}
	
	@GetMapping("/stock/daily_summary")
	public ResponseEntity<Map<String,Object>> datedailystocksummary(@RequestParam("date") String date, 
            @RequestParam("productCode") String productCode){
		
		Map<String, Object> response = new HashMap<>();
	    try {
	        LocalDate stockDate = LocalDate.parse(date);

	        ViewDailyStockSummaryEntity dailySummary = viewDailyStockSummaryService.getDailyStockSummary(stockDate, productCode);

	        response.put("data", dailySummary);
	        response.put("message", "재고 요약 정보를 성공적으로 가져왔습니다.");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("error", "오류가 발생했습니다.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@GetMapping("/stock/inventorysupplierlist/{pno}")
	public Map<String, Object> inventorysupplierlist(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Map<String, Object> result = new HashMap<>();
		
		 Page<SupplierEntity> supplierlistdata;
		    if (code == null || code.isEmpty() || word == null || word.isEmpty()) {
		        supplierlistdata = supplierService.getPaginatedData(pno, modalSIZE);
		    } else {
		        supplierlistdata = supplierService.getPaginatedData(pno, modalSIZE, code, word);
		    }
		    
		    if (supplierlistdata == null || supplierlistdata.isEmpty()) {
		        supplierlistdata = Page.empty();
		    }
		    
		    result.put("supplierlistdata", supplierlistdata);
		    return result;
		
	}
		
	
	 @PutMapping("/stock/updateSafetyQty/{productCode}")
	    public ResponseEntity<Map<String, String>> updateSafetyQty(
	            @PathVariable String productCode, 
	            @RequestBody Map<String, Integer> body) {
		 	
		 	Integer safetyQty = body.get("safetyQty");
		 	 if (safetyQty == null) {
		         return ResponseEntity.badRequest().body(Map.of("error", "안전 재고 수량이 필요합니다."));
		     }
		 
	        boolean isUpdated = stockService.updateSafetyQty(productCode, safetyQty);
	        
	        if (isUpdated) {
	        	return ResponseEntity.ok(Map.of("success", "안전 재고 수량이 성공적으로 업데이트되었습니다."));
	        } else {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "해당 제품의 재고를 찾을 수 없습니다."));
	        }
	    }
	
	
	   
	 @PostMapping("/stock/inventories")
	 public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
	        // JWT 토큰에서 매니저 정보 추출
	        String token = jwtTokenProvider.resolveToken(request);
	        if (token == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // JWT 토큰이 없으면 401 반환
	        }

	        String manager = jwtTokenProvider.getName(token); // 토큰에서 매니저 이름 추출

	        if (manager == null || manager.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 매니저 정보가 없으면 400 에러 반환
	        }

	        try {
	            ProductEntity saveproduct = productService.saveProduct(productDto, manager);
	            ProductDto saveproductDto = productService.convertToProductDto(saveproduct);
	            return ResponseEntity.ok(saveproductDto);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	        }
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
	 
	@Autowired
	private ViewProductStockSupplierService viewProductStockService;
	
	@GetMapping("/stock/viewproductstocks/{pno}")
	public Page<ViewProductStockSupplierEntity> viewproductstockList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewProductStockSupplierEntity> result = viewProductStockService.getPaginatedData(pno, SIZE);
    	if (code.equals("") || word.equals("")) {
			result = viewProductStockService.getPaginatedData(pno, SIZE);
		} else {
			result = viewProductStockService.getPaginatedData(pno, SIZE, code, word);
		}
    	return result;
		
	}
	
	@GetMapping("/stock/inventorymanagement/{pno}")
	public Map<String, Object> inventorymanagementList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Map<String, Object> result = new HashMap<>();
		Page<ViewProductStockSupplierEntity> inventoryData = viewProductStockService.getPaginatedData(pno, SIZE);
		if (code.equals("") || word.equals("")) {
			inventoryData = viewProductStockService.getPaginatedData(pno, SIZE);
		} else {
			inventoryData = viewProductStockService.getinventoryPaginatedData(pno, SIZE, code, word);
		}
		
		List<CategoryDto> categories = categoryService.getAllOrdersByCode();
		result.put("inventoryData", inventoryData);
	    result.put("categories", categories);
		
		return result;
		
	}
	
	 @GetMapping("/stock/inventorymodify/{productCode}")
	    public ResponseEntity<ViewProductStockSupplierDto> getInventoryForModify(@PathVariable String productCode) {
	        ViewProductStockSupplierDto productDto = viewProductStockSupplierService.getProductStockDtoByCode(productCode);
	        if (productDto != null) {
	            return ResponseEntity.ok(productDto);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	 
	 @PutMapping("/stock/inventorymodifysave/{productCode}")
	    public void updateProductInfo(
	            @PathVariable String productCode,
	            @RequestBody ProductDto productDto) {
	        productService.updateProductInfo(productCode, productDto);
	    }
	 
	 @DeleteMapping("/stock/inventorydelete/{productCode}")
	    public ResponseEntity<String> deleteProduct(@PathVariable String productCode) {
	        boolean isDeleted = productService.deleteProductWithStockCheck(productCode);

	        if (isDeleted) {
	            return ResponseEntity.ok("상품이 삭제되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품을 삭제하는데 실패했습니다.");
	        }
	    }
	
	
	//******카테고리 모달 부분*****//
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/stock/categories/{pno}")
	public Page<CategoryEntity> categoryList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		
    	Page<CategoryEntity> result = categoryService.getPaginatedData(pno, SIZE);
    	if (code == null || code.isEmpty() || word == null || word.isEmpty()) {
			result = categoryService.getPaginatedData(pno, modalSIZE);
		} else {
			result = categoryService.getPaginatedData(pno, modalSIZE, code, word);
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
        
        return ResponseEntity.ok(categoryresponse);
    }
}