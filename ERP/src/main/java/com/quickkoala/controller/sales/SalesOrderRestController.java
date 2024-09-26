package com.quickkoala.controller.sales;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.dto.sales.SearchProductCodeDTO;
import com.quickkoala.entity.sales.ClientsOrdersEntity;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.repository.stock.ProductRepository;
import com.quickkoala.service.client.SupplierServiceImpl;
import com.quickkoala.service.sales.SalesOrderServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sales")
public class SalesOrderRestController {

    @Autowired
    private SalesOrderServiceImpl orderService;
    
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SupplierServiceImpl supplierService;

	//주문등록 (액셀 등록)
	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
	    try {
	        // 파일 변환 및 저장 위치 설정
	        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
	        file.transferTo(convFile);

	        // 파일 파싱 및 주문 저장
	        List<ClientsOrdersDTO> orders = orderService.parseExcelFile(convFile);
	        String token = jwtTokenProvider.resolveToken(request);
	        
	        // 주문 저장 후 중복된 주문 정보 반환받기
	        List<String> duplicateOrders = orderService.saveOrder(orders, token);

	        // 메시지 생성
	        StringBuilder message = new StringBuilder("등록 완료<br>");
	        if (!duplicateOrders.isEmpty()) {
	            message.append("중복된 주문이 있어 제외하였습니다 : <br>");
	            message.append(String.join("<br> ", duplicateOrders));  // 중복된 주문 정보 추가
	        }

	        // 응답 시 Content-Type을 text/html로 설정하여 줄바꿈 적용
	        return ResponseEntity.ok()
	                             .contentType(MediaType.TEXT_HTML)  // HTML 컨텐츠 타입 설정
	                             .body(message.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_HTML)
	                             .body("<p>파일 처리 중 오류가 발생했습니다.</p>");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_HTML)
	                             .body("<p>주문 등록 실패</p>");
	    }
	}

	
    //직접 주문등록
	@PostMapping("/saveOrder")
	public ResponseEntity<?> saveOrder(@RequestBody List<ClientsOrdersDTO> orders, HttpServletRequest request) {
	    try {
	        // 주문 정보 저장 로직
	        String token = jwtTokenProvider.resolveToken(request);
	        orderService.saveOrder(orders, token);
	        return ResponseEntity.ok(Collections.singletonMap("message", "주문이 성공적으로 등록되었습니다."));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("message", "주문 등록 실패"));
	    }
	}


    
    //주문완료 (주문 상품 출력)
	// 주문완료 (주문 상품 출력)
	@GetMapping("/{orderId}/products")
	public List<ClientsOrderProductsDTO> getOrderProducts(@PathVariable("orderId") String orderId) {
	    return orderService.getOrderProductsByOrderId(orderId);
	}
    
 // 검색 및 페이징 처리 엔드포인트
    @GetMapping("/filter")
    public Map<String, Object> filterOrders(
            HttpServletRequest request, // request 객체 추가
            @RequestParam("searchType") String searchType,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        // JWT 토큰에서 code 추출
        String token = jwtTokenProvider.resolveToken(request);
        String code = jwtTokenProvider.getClaim(token, "code");

        LocalDate startDate = null;
        LocalDate endDate = null;

        // 시작 날짜와 종료 날짜를 파싱하여 LocalDate로 변환
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_DATE);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_DATE);
        }

        // Service를 통해 검색 및 페이징 처리 (companyCode 추가)
        Page<ClientsOrdersEntity> ordersPage = orderService.searchOrders(
                code, searchType, searchText, startDate, endDate, page, size);

        // 결과를 DTO로 변환하여 필요한 데이터만 반환
        Map<String, Object> response = new HashMap<>();
        response.put("content", ordersPage.getContent()); // 실제 데이터 리스트
        response.put("currentPage", ordersPage.getNumber()); // 현재 페이지 번호
        response.put("totalItems", ordersPage.getTotalElements()); // 전체 아이템 수
        response.put("totalPages", ordersPage.getTotalPages()); // 전체 페이지 수

        return response;
    }

    
    // 상품코드에 맞는 상품명 조회
    @GetMapping("/getProductByCode")
    public ResponseEntity<SearchProductCodeDTO> getProductByCode(@RequestParam String code) {
        Optional<ProductEntity> product = productRepository.findByCode(code);

        if (product.isPresent()) {
            SearchProductCodeDTO productDTO = new SearchProductCodeDTO(product.get().getCode(), product.get().getName());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
//    @GetMapping("receive/autocomplete") 
//    public List<SupplierEntity> autocomplete(@RequestParam String term) {
//    	return supplierService.searchByName(term); 
//	  }
    
}
