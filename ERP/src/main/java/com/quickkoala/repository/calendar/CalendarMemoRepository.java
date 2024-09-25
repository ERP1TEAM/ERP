package com.quickkoala.repository.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.calender.CalendarMemoEntity;

public interface CalendarMemoRepository extends JpaRepository<CalendarMemoEntity, Long> {
    // code를 기준으로 메모 조회
    List<CalendarMemoEntity> findByCode(String code);
    
}
