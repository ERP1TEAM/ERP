package com.quickkoala.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.service.WarehouseService;

@Controller
@RequestMapping("warehouse")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseservice;
	
	@GetMapping("/locationStatus")
	public String locationStatus() {
		
		return "warehouse/locationStatus";
	}
	
	 // 창고 정보를 비동기적으로 가져오는 메소드
    @CrossOrigin(origins="*",allowedHeaders = "*")
	@GetMapping("/warehouse-info")
    @ResponseBody
    public ResponseEntity<List<WarehouseEntity>> warehouseList() {
        
    	List<WarehouseEntity> warehouselist=warehouseservice.getAllOrdersByCode();
    	
    	return ResponseEntity.ok(warehouselist);
    }
}
