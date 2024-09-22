package com.quickkoala.controller.sales;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.entity.sales.ClientsOrdersEntity;
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

    //주문등록 (액셀 등록)
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            
            // 파일 파싱 및 저장 로직
            List<ClientsOrdersDTO> orders = orderService.parseExcelFile(convFile);
            orderService.saveOrder(orders);

            return ResponseEntity.ok("주문 등록 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 등록 실패");
        }
    }
    
    //주문완료 (주문 상품 출력)
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
            @RequestParam(value = "searchDate", required = false) String searchDateStr,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        // JWT 토큰에서 code 추출
        String token = jwtTokenProvider.resolveToken(request);
        String code = jwtTokenProvider.getClaim(token, "code");

        LocalDate searchDate = null;
        if (searchDateStr != null && !searchDateStr.isEmpty()) {
            searchDate = LocalDate.parse(searchDateStr, DateTimeFormatter.ISO_DATE);
        }

        // Service를 통해 검색 및 페이징 처리 (companyCode 추가)
        Page<ClientsOrdersEntity> ordersPage = orderService.searchOrders(
                code, searchType, searchText, searchDate, page, size);

        // 결과를 DTO로 변환하여 필요한 데이터만 반환
        Map<String, Object> response = new HashMap<>();
        response.put("content", ordersPage.getContent()); // 실제 데이터 리스트
        response.put("currentPage", ordersPage.getNumber()); // 현재 페이지 번호
        response.put("totalItems", ordersPage.getTotalElements()); // 전체 아이템 수
        response.put("totalPages", ordersPage.getTotalPages()); // 전체 페이지 수

        return response;
    }

}
