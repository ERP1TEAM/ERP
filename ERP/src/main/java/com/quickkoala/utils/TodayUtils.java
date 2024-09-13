package com.quickkoala.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodayUtils {
	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		return today;
	}
	
	public static String getTodayS() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String today = sdf.format(date);
		return today;
	}
	
	public static String getSevenDaysAfter() {
        Date today = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        
        Date futureDate = calendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(futureDate);
	}
}
