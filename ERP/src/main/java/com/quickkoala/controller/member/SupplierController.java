package com.quickkoala.controller.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.SupplierEntity;
import com.quickkoala.service.SupplierService;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/checkSupplier")
    public ResponseEntity<Map<String, Object>> checkSupplier(@RequestParam String code) {
        Map<String, Object> response = new HashMap<>();
        Optional<SupplierEntity> supplier = supplierService.findByCode(code);

        if (supplier.isPresent()) {
            response.put("success", true);
            response.put("name", supplier.get().getName());
        } else {
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }
}


