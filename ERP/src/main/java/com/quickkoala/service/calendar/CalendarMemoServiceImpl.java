package com.quickkoala.service.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.calender.CalendarMemoEntity;
import com.quickkoala.repository.calendar.CalendarMemoRepository;

import java.util.List;

@Service
public class CalendarMemoServiceImpl implements CalendarMemoService {

    @Autowired
    private CalendarMemoRepository memoRepository;

    @Override
    public CalendarMemoEntity saveMemo(CalendarMemoEntity memo) {
        return memoRepository.save(memo);
    }

    @Override
    public List<CalendarMemoEntity> getAllMemos() {
        return memoRepository.findAll();
    }
    
}

