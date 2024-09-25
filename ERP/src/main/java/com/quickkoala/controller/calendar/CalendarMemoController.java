package com.quickkoala.controller.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.calender.CalendarMemoEntity;
import com.quickkoala.service.calendar.CalendarMemoServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/memos")
public class CalendarMemoController {

    @Autowired
    private CalendarMemoServiceImpl memoService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // 메모 저장 API
    @PostMapping("/save")
    public CalendarMemoEntity saveMemo(@RequestBody CalendarMemoEntity memo) {
        return memoService.saveMemo(memo);
    }

    // 특정 코드에 해당하는 메모만 조회 API
    @GetMapping("/all")
    public List<CalendarMemoEntity> getMemosByCode(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String code = jwtTokenProvider.getCode(token);
        
        return memoService.getMemosByCode(code); // 코드 값으로 메모 조회
    }
    
    // 메모 삭제 API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id) {
        boolean isDeleted = memoService.deleteMemo(id);
        if (isDeleted) {
            return ResponseEntity.ok("메모가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("메모를 찾을 수 없습니다.");
        }
    }
}
