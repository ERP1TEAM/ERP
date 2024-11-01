package com.quickkoala.controller.stock;

import java.util.Arrays;
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

import com.quickkoala.dto.stock.LocationDto;
import com.quickkoala.dto.stock.ViewLotViewProductStockSupplierDto;
import com.quickkoala.dto.stock.WarehouseDto;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.WarehouseEntity;
import com.quickkoala.service.stock.LocationService;
import com.quickkoala.service.stock.ViewLotViewProductStockSupplierService;
import com.quickkoala.service.stock.WarehouseService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WarehouseRestController {

	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/stock/warehouses")
    public ResponseEntity<List<WarehouseDto>> warehouseList() {
    	List<WarehouseDto> warehouseList=warehouseService.getAllOrdersByCode();
    	return ResponseEntity.ok(warehouseList);
    }
	
    @PostMapping("/stock/warehouses")
    public ResponseEntity<String> registerWarehouse(@RequestBody WarehouseDto warehouseDto) {
    	WarehouseEntity warehousesave = warehouseService.saveWarehouse(warehouseDto);
        if(warehousesave == null) {
    	return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 창고 코드 입니다.");
        }
    	return ResponseEntity.ok("창고가 등록되었습니다.");
    }
    
    @DeleteMapping("/stock/warehouses/{warehouseCodes}")
    public ResponseEntity<Map<String, Object>> deleteWarehouses(@PathVariable List<String> warehouseCodes) {
        Map<String, Object> deleteResults = warehouseService.deleteWarehouse(warehouseCodes);

        if (deleteResults.values().stream().anyMatch(result -> result.equals("삭제되었습니다."))) {
            return ResponseEntity.ok(deleteResults);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteResults);
        }
    }
	
	@GetMapping("/stock/{warehouseCode}")
	public ResponseEntity<WarehouseDto> getWarehouse(@PathVariable String warehouseCode) {
	     WarehouseDto warehouseDto = warehouseService.getWarehouseByCode(warehouseCode);
	     if (warehouseDto != null) {
	    	 return ResponseEntity.ok(warehouseDto);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     }
	}
	
	@PutMapping("/stock/{warehouseCode}")
	public ResponseEntity<String> updateWarehouse(@PathVariable String warehouseCode, @RequestBody WarehouseDto warehouseDto){
		boolean updateWarehouse = warehouseService.updateWarehouse(warehouseCode, warehouseDto);
		
		if(updateWarehouse) {
			return ResponseEntity.ok("창고가 수정되었습니다.");
		}else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 창고 코드를 찾을 수 없습니다.");
		}
	}
	
	//창고검색
	@GetMapping("/stock/warehousesearch")
	public ResponseEntity<List<WarehouseDto>> searchWarehouse
		(@RequestParam("warehouseSearchtype") String warehouseSearchtype,
				@RequestParam("warehouseSearch") String warehouseSearch){
		List<WarehouseDto> warehouseSearchresult=warehouseService.searchWarehouse(warehouseSearchtype, warehouseSearch);
			
			return ResponseEntity.ok(warehouseSearchresult);
	}
		
	/*
		//창고 리스트 + 페이징
		@GetMapping("/stock/warehouses/{pno}")
		public Page<WarehouseEntity> warehouseList(@PathVariable Integer pno, @RequestParam String code,
				@RequestParam String word) {
	    	Page<WarehouseEntity> result = warehouseService.getPaginatedData(pno, SIZE);
	    	if (code.equals("") || word.equals("")) {
				result = warehouseService.getPaginatedData(pno, SIZE);
			} else {
				result = warehouseService.getPaginatedData(pno, SIZE, code, word);
			}
	    	return result;
	    	
		}
		 
	*/
	//****location 부분****//
	
	private final int SIZE = 10;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ViewLotViewProductStockSupplierService viewlotViewpssService;
	
	// 로케이션 등록
	@PostMapping("/stock/locations")
	public ResponseEntity<LocationDto> saveLocation(@RequestBody LocationDto locationDto) {
	    try {
	        LocationEntity savelocation = locationService.saveLocation(locationDto);
	        LocationDto savelocationDto = locationService.convertToLocationDto(savelocation);
	        return ResponseEntity.ok(savelocationDto);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
	
	//로케이션 리스트
	@GetMapping("/stock/locations/{pno}")
	public Page<LocationEntity> locationList(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
    	Page<LocationEntity> result = locationService.getPaginatedData(pno, SIZE);
    	if (code.equals("") || word.equals("")) {
			result = locationService.getPaginatedData(pno, SIZE);
		} else {
			result = locationService.getPaginatedData(pno, SIZE, code, word);
		}
    	return result;
	}
	
	//로케이션 삭제
    @DeleteMapping("/stock/locations/{locationCodes}")
    public ResponseEntity<Map<String, Object>> deletelocations(@PathVariable String locationCodes) {
    	List<String> locationCodeList = Arrays.asList(locationCodes.split(","));
        Map<String, Object> locationdelresponse = locationService.deleteLocation(locationCodeList);
        
        return ResponseEntity.ok(locationdelresponse);
    }
    
    @GetMapping("/stock/locationstatus")
    public List<LocationDto> getlocationstatusdata(){
    	return locationService.getAllLocations();
    }
    
    @GetMapping("/stock/locationstatuslist/{locationCode}")
    public ResponseEntity<List<ViewLotViewProductStockSupplierDto>> getProductsByLocation(@PathVariable String locationCode) {
    	List<ViewLotViewProductStockSupplierDto> products = viewlotViewpssService.getAllProductsByLocation(locationCode);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }
    
    //로케이션 수정 모달
    @GetMapping("/stock/locationmodify/{code}")
    public ResponseEntity<LocationDto> getLocationByCode(@PathVariable String code) {
        try {
            LocationDto locationDto = locationService.getLocationByCode(code);
            return ResponseEntity.ok(locationDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("/stock/locationmodify/{locationCode}")
    public ResponseEntity<String> updateLocation(@PathVariable String locationCode, @RequestBody LocationDto locationDto) {
        try {
        	locationDto.setCode(locationCode);
        	locationService.updateLocation(locationDto);
            
            return ResponseEntity.ok("로케이션 수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("로케이션 수정 중 오류가 발생했습니다.");
        }
    }
    
}
