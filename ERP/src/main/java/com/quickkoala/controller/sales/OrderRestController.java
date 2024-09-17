package com.quickkoala.controller.sales;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.ClientsOrderProductsDTO;
import com.quickkoala.dto.ClientsOrdersDTO;
import com.quickkoala.service.OrderServiceImpl;

@RestController
@RequestMapping("/sales")
public class OrderRestController {

    @Autowired
    private OrderServiceImpl orderService;

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

}
