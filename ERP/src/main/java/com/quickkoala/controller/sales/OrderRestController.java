package com.quickkoala.controller.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.quickkoala.dto.ClientsOrdersDTO;
import com.quickkoala.entity.ClientsOrdersEntity;
import com.quickkoala.service.OrderServiceImpl;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class OrderRestController {

    @Autowired
    private OrderServiceImpl orderService;

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
    

}
