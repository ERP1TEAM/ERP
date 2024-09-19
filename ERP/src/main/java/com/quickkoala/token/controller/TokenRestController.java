package com.quickkoala.token.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.quickkoala.token.config.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class TokenRestController {
	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키를 찾습니다.
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    // 쿠키를 무효화합니다.
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        // 로그아웃 후 사용자를 login.do로 리디렉션합니다.
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            // 리디렉션 중 오류 처리
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            try {
                response.getWriter().write("Logout failed");
            } catch (IOException ioException) {
                // 추가적인 오류 처리
            }
        }
    }
    
    
    @PostMapping("/token-remaining-time")
    public ResponseEntity<?> getTokenRemainingTime(HttpServletRequest request) {
        // 쿠키에서 토큰을 추출
        Cookie[] cookies = request.getCookies();
        String token = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 토큰이 없거나 유효하지 않으면 에러 응답 반환
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        try {
            // 토큰에서 만료 시간 추출
            Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenProvider.getSecretKeyBytes())
                .parseClaimsJws(token)
                .getBody();

            Date expirationTime = claims.getExpiration();
            Date now = new Date();

            // 남은 시간 계산 (ms)
            long remainingTimeMillis = expirationTime.getTime() - now.getTime();

            return ResponseEntity.ok().body(remainingTimeMillis);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
    
    
    
    @PostMapping("/extend-session")
    public ResponseEntity<?> extendSession(HttpServletRequest request) {
        // 쿠키에서 토큰을 추출
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Date now = new Date();
            Date newExpirationTime = new Date(now.getTime() + jwtTokenProvider.getValidityInMilliseconds());
            String newToken = jwtTokenProvider.createToken(jwtTokenProvider.getUsername(token), jwtTokenProvider.getRole(token));

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            if (response != null) {
                // 기존 쿠키를 제거
                Cookie oldCookie = new Cookie("token", null);
                oldCookie.setPath("/");
                oldCookie.setMaxAge(0);
                response.addCookie(oldCookie);

                // 새로운 쿠키를 설정
                Cookie tokenCookie = new Cookie("token", newToken);
                tokenCookie.setHttpOnly(true);
                tokenCookie.setSecure(true);
                tokenCookie.setPath("/");
                tokenCookie.setAttribute("SameSite", "Strict");
                response.addCookie(tokenCookie);

                return ResponseEntity.ok().body("Session extended");
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request or response is null");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    
}
