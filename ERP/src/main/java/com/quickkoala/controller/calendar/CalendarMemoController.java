package com.quickkoala.controller.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.entity.calender.CalendarMemoEntity;
import com.quickkoala.service.calendar.CalendarMemoServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

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

    // 모든 메모 조회 API
    @GetMapping("/all")
    public List<CalendarMemoEntity> getAllMemos() {
        return memoService.getAllMemos();
    }	
}
