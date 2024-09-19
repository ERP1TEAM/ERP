package com.quickkoala.controller.member;

import com.quickkoala.entity.SupplierEntity; 
import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.entity.sales.SalesEntity;
import com.quickkoala.service.MemberService;
import com.quickkoala.service.MemberServiceImpl;
import com.quickkoala.service.SalesServiceImpl;
import com.quickkoala.service.SupplierService;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private MemberServiceImpl memberService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SalesServiceImpl salesService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //관리자 등록
    @PostMapping("/register")
    public String register(@RequestBody MemberEntity member) {
        memberService.saveMember(member);
        return "회원 등록 성공";
    }
    
    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberEntity member, HttpServletResponse response) {
        boolean isAuthenticated = memberService.authenticateUser(member.getUserId(), member.getPassword());
        if (isAuthenticated) {
        	String role= memberService.getRole(member.getUserId());
            String token = jwtTokenProvider.createToken(member.getUserId(), role);
            // JWT를 HTTP-Only 쿠키에 설정
            jwtTokenProvider.setJwtCookies(response, token);

            // 역할에 따라 리디렉션 URL을 결정
            String redirectUrl;
            switch (role) {
                case "Sales":
                    redirectUrl = "/sales/home";
                    break;
                case "Supplier":
                    redirectUrl = "/supplier/home";
                    break;
                case "Distributor":
                    redirectUrl = "/main/home";
                    break;
                default:
                    redirectUrl = "/login?error=true";
                    break;
            }

            // 리디렉션 URL을 응답으로 반환
            return ResponseEntity.ok(redirectUrl);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
        
    // 관리자 등록 - 소속사 코드 확인
    @GetMapping("/checkCompany")
    public ResponseEntity<Map<String, Object>> checkCompany(@RequestParam String code) {
        Map<String, Object> response = new HashMap<>();

        if (code.startsWith("SP")) {
            // Supplier 확인 로직
            Optional<SupplierEntity> supplier = supplierService.findByCode(code);
            if (supplier.isPresent()) {
                response.put("success", true);
                response.put("name", supplier.get().getName());
            } else {
                response.put("success", false);
            }
        } else if (code.startsWith("SL")) {
            // Sales 확인 로직
            Optional<SalesEntity> sales = salesService.findByCode(code);
            if (sales.isPresent()) {
                response.put("success", true);
                response.put("name", sales.get().getName());
            } else {
                response.put("success", false);
            }
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
