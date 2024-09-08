package com.quickkoala.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stock")
public class StockController {
	
	@GetMapping("/inventoryIn")
	public String inventoryIn() {
		z
		return "stock/inventoryIn.html";
	}
	
	@GetMapping("/inventoryList")
	public String inventoryList() {
		
		return "stock/inventoryList.html";
	}
	
	 // 창고 정보를 비동기적으로 가져오는 메소드
    @CrossOrigin(origins="*",allowedHeaders = "*")
	@GetMapping("/warehouse-info")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getWarehouseInfo() {
        // 예시 창고 데이터 (데이터베이스와 연동할 경우 여기를 수정)
        Map<String, String> warehouseInfo = new HashMap<>();
        warehouseInfo.put("message", "남양주 창고의 상세 정보가 여기에 표시됩니다.");

        // ResponseEntity로 JSON 형식으로 데이터 반환
        return ResponseEntity.ok(warehouseInfo);
    }
}

