package com.quickkoala.controller.member;

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

import com.quickkoala.dto.member.MemberDTO;
import com.quickkoala.entity.client.SalesEntity;
import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.entity.member.MemberEntity;
import com.quickkoala.service.client.SalesService;
import com.quickkoala.service.client.SupplierService;
import com.quickkoala.service.member.MemberServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class MemberRestController {

    @Autowired
    private MemberServiceImpl memberService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SalesService salesService;
    
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
            // 사용자 정보 조회
            MemberDTO memberInfo = memberService.getMemberInfo(member.getUserId());

            // 토큰 생성: userId, role, name, company 정보를 포함
            String token = jwtTokenProvider.createToken(member.getUserId(), memberInfo.getRole(), memberInfo.getCode(), memberInfo.getName());

            // JWT를 HTTP-Only 쿠키에 설정
            jwtTokenProvider.setJwtCookies(response, token);

            // 역할에 따라 리디렉션 URL 결정
            String redirectUrl;
            switch (memberInfo.getRole()) {
                case "Sales":
                    redirectUrl = "/sales/home";
                    break;
                case "Supplier":
                    redirectUrl = "/supplier/home";
                    break;
                case "Main":
                    redirectUrl = "/main/home";
                    break;
                default:
                    redirectUrl = "/login?error=true";
                    break;
            }

            // 리디렉션 URL을 응답으로 반환
            return ResponseEntity.ok(redirectUrl);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디나 패스워드를 확인해주세요");
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
