package com.quickkoala.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickkoala.entity.calender.CalendarMemoEntity;

public interface CalendarMemoRepository extends JpaRepository<CalendarMemoEntity, Long> {
}
