package org.vito.calendar;

import java.util.Calendar;

public class CalcWeek {
	public static void main(String args[]){
		Calendar calendar = Calendar.getInstance();
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);
//		calendar.setMinimalDaysInFirstWeek(4);  //
		
//		calendar = setAsUKWeekNumbering(calendar);
		calendar = setAsISOWeekNumbering(calendar);
		
		int weekNO = calendar.get(Calendar.WEEK_OF_YEAR);
		
		System.out.println("Week of the year: W" + weekNO);
	}
	
	/**
	 * 星期的计数方式. UK的一种计数法.
	 * 一周第一天是周一.
	 * 每年第一周包含: 1月1日, 第一个周日.
	 * 星期的计数可以被赋给两次. (比如2010.1.1是周五, 1月1日这一周可以算为2009的第53周, 
	 * 也是2010的第一周.)
	 * @param calendar
	 * @return
	 */
	static Calendar setAsUKWeekNumbering(Calendar calendar){
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
		return calendar;
	}
	
	/**
	 * ISO8601计数法.
	 * 一周的第一天是周一.
	 * 每年第一周定义为包括第一个周四的那一周.
	 * 星期计数不能赋给两次. (Weeks not assigned twice.)
	 * @param calendar
	 * @return
	 */
	static Calendar setAsISOWeekNumbering(Calendar calendar){
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		//包含第一个周四, 即最少包含Thu.,Fri.,Sat.,Sun.四天.
		calendar.setMinimalDaysInFirstWeek(4);  
		return calendar;
	}
}
