package com.quickkoala.utils;

import java.text.SimpleDateFormat;
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
	
}
