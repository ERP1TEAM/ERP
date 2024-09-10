package com.quickkoala.controller;

import java.util.List;
import java.util.Map;

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
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/locationStatus")
	public String locationStatus() {
		
		return "warehouse/locationStatus";
	}
	
	@GetMapping("/warehouse-info")
    @ResponseBody
    public ResponseEntity<List<WarehouseEntity>> warehouseList() {
        
    	List<WarehouseEntity> warehouseList=warehouseService.getAllOrdersByCode();
    	
    	return ResponseEntity.ok(warehouseList);
    }
 
    @GetMapping("/warehousein-modal")
    public String warehousein() {
    	return "warehouse/warehouseModal :: warehouseInModalContent";
    }

    @PostMapping("/warehouse-register")
    public ResponseEntity<String> registerWarehouse(@RequestBody WarehouseEntity warehouseEntity) {
    	warehouseService.saveWarehouse(warehouseEntity);
        return ResponseEntity.ok("창고가 등록되었습니다.");
    }
    
    @PostMapping("/warehouse-delete")
    public ResponseEntity<Map<String, Object>> deleteWarehouses(@RequestBody Map<String, List<String>> request) {
        List<String> warehouseCode = request.get("warehouseCode");
        Map<String, Object> response =  warehouseService.deleteWarehouse(warehouseCode);
       
        return ResponseEntity.ok().body(response);

    }
}
