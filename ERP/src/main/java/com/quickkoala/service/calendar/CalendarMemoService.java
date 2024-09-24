package com.quickkoala.service.calendar;

import java.util.List;

import com.quickkoala.entity.calender.CalendarMemoEntity;

public interface CalendarMemoService {
	CalendarMemoEntity saveMemo(CalendarMemoEntity memo);
	List<CalendarMemoEntity> getMemosByCode(String code);
}
