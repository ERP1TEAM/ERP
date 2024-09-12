package com.quickkoala.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.WarehouseDto;
import com.quickkoala.entity.WarehouseEntity;
import com.quickkoala.service.WarehouseService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WarehouseRestController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/warehouse/warehouses")
    public ResponseEntity<List<WarehouseDto>> warehouseList() {
    	List<WarehouseDto> warehouseList=warehouseService.getAllOrdersByCode();
    	return ResponseEntity.ok(warehouseList);
    }
	
    @PostMapping("/warehouse/warehouses")
    public ResponseEntity<String> registerWarehouse(@RequestBody WarehouseDto warehouseDto) {
    	boolean insave = warehouseService.saveWarehouse(warehouseDto);
        if(!insave) {
    	return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 창고 코드 입니다.");
        }
    	return ResponseEntity.ok("창고가 등록되었습니다.");
    }
    
    @DeleteMapping("/warehouse/{warehouseCodes}")
    public ResponseEntity<Map<String, Object>> deleteWarehouses(@PathVariable String warehouseCodes) {
    	List<String> warehouseCodeList = Arrays.asList(warehouseCodes.split(","));
        Map<String, Object> warehouseresponse = warehouseService.deleteWarehouse(warehouseCodeList);
        
        warehouseresponse.put("ok", true);
        
        return ResponseEntity.ok(warehouseresponse);
    }
	
	@GetMapping("/warehouse/{warehouseCode}")
	public ResponseEntity<WarehouseDto> getWarehouse(@PathVariable String warehouseCode) {
	     WarehouseDto warehouseDto = warehouseService.getWarehouseByCode(warehouseCode);
	     if (warehouseDto != null) {
	    	 return ResponseEntity.ok(warehouseDto);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     }
	}
	
	@PutMapping("/warehouse/{warehouseCode}")
	public ResponseEntity<String> updateWarehouse(@PathVariable String warehouseCode, @RequestBody WarehouseDto warehouseDto){
		boolean updateWarehouse = warehouseService.updateWarehouse(warehouseCode, warehouseDto);
		
		if(updateWarehouse) {
			return ResponseEntity.ok("창고가 수정되었습니다.");
		}else {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 창고 코드를 찾을 수 없습니다.");
		}
	}
}
