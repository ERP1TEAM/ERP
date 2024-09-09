package com.quickkoala.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.service.WarehouseService;

@Controller
@RequestMapping("warehouse")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/locationStatus")
	public String locationStatus() {
		
		return "warehouse/locationStatus";
	}
	
	 // 창고 정보를 비동기적으로 가져오는 메소드
    @CrossOrigin(origins="*",allowedHeaders = "*")
	@GetMapping("/warehouse-info")
    @ResponseBody
    public ResponseEntity<List<WarehouseEntity>> warehouseList() {
        
    	List<WarehouseEntity> warehouseList=warehouseService.getAllOrdersByCode();
    	
    	return ResponseEntity.ok(warehouseList);
    }
 
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/warehousein-modal")
    public String warehousein() {
    	return "warehouse/warehouseModal :: warehouseInModalContent";  // Thymeleaf 템플릿을 반환
    }

    @PostMapping("/warehouse-register")
    public ResponseEntity<String> registerWarehouse(@RequestBody WarehouseEntity warehouseEntity) {
        warehouseService.saveWarehouse(warehouseEntity);
        return ResponseEntity.ok("창고가 성공적으로 등록되었습니다.");
    }
}
