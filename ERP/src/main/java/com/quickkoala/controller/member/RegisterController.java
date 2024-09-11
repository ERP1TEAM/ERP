package com.quickkoala.controller.member;

import com.quickkoala.entity.SupplierEntity;
import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.service.MemberService;
import com.quickkoala.service.SupplierService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private SupplierService supplierService;

    //관리자 등록
    @PostMapping("/register")
    public String register(@RequestBody MemberEntity member) {
        memberService.saveMember(member);
        return "회원 등록 성공";
    }
    
    //관리자 등록 - 소속사 코드 확인
    @GetMapping("/checkCompany")
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
    
    //관리자 등록 - 아이디 중복체크
    @GetMapping("/checkId")
    public ResponseEntity<Boolean> checkId(@RequestParam String userId) {
        boolean exists = memberService.isUserIdExists(userId);
        return ResponseEntity.ok(exists);
    }
}
