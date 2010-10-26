/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------
 * SerialDate.java
 * ---------------
 * (C) Copyright 2001-2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * 
 */

package org.jfree.date;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  <pre>
 *  An abstract class that defines our requirements for manipulating dates,
 *  without tying down a particular implementation.
 *  
 *  Requirement 1 : match at least what Excel does for dates;
 *  Requirement 2 : the date represented by the class is immutable;
 *  
 *  Why not just use java.util.Date?  We will, when it makes sense.  At times,
 *  java.util.Date can be *too* precise - it represents an instant in time,
 *  accurate to 1/1000th of a second (with the date itself depending on the
 *  time-zone).  Sometimes we just want to represent a particular day (e.g. 21
 *  January 2015) without concerning ourselves about the time of day, or the
 *  time-zone, or anything else.  That's what we've defined SerialDate for.
 *  
 *  You can call getInstance() to get a concrete subclass of SerialDate,
 *  without worrying about the exact implementation.
 *  
 *  Refactored from JCommon SerialDate.java by David Gilbert, some fixes and 
 *  refactored based on Clean Code by Robert C. Martin.
 *	
 *
 * @author David Gilbert
 * @author Robert C. Martin did a lot of refactoring.
 */
public abstract class DayDate implements Comparable,Serializable{
    	
	
    /** For serialization. 
     * Robert deleted the variable as he thought it's safer to him with 
     * automatic control of serialization.
     */
//    private static final long serialVersionUID = -293716040467423637L;    
    
    public static final DateFormatSymbols
        DATE_FORMAT_SYMBOLS = new SimpleDateFormat().getDateFormatSymbols();    

    /** The number of days in a (non-leap) year up to the end of each month. */
//    static final int[] AGGREGATE_DAYS_TO_END_OF_MONTH =
//        {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

    /** The number of days in a year up to the end of the preceding month. */
    private static final int[] AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
        {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

    /** The number of days in a leap year up to the end of each month. */
//    static final int[] LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_MONTH =
//        {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    /** 
     * The number of days in a leap year up to the end of the preceding month. 
     */
    private static final int[] 
        LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
            {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};  

    /**
     * Returns an array of month names.
     *
     * @return an array of month names.
     */
//    public static String[] getMonthNames() {
//    	return DATE_FORMAT_SYMBOLS.getMonths();
//    }   

    /**
     * Determines whether or not the specified year is a leap year.
     *
     * @param yyyy  the year (in the range 1900 to 9999).
     *
     * @return <code>true</code> if the specified year is a leap year.
     */
//    public static boolean isLeapYear(int year) {
//    	boolean fourth = year % 4 == 0;
//		boolean hundredth = year % 100 == 0;
//		boolean fourHundredth = year % 400 == 0;
//		return fourth && (!hundredth || fourHundredth);
//    }

    /**
     * Returns the number of the last day of the month, taking into account 
     * leap years.
     *
     * @param month  the month.
     * @param yyyy  the year (in the range 1900 to 9999).
     *
     * @return the number of the last day of the month.
     */
//    public static int lastDayOfMonth(Month month, int year) {
//    	if(month == Month.FEBRUARY && isLeapYear(year)){
//			return month.lastDay() + 1;
//		}else {
//			return month.lastDay();
//		}
//    }
    
    int correctLastDayOfMonth(int day, Month month, int year){
    	int lastDayOfMonth = DateUtil.lastDayOfMonth(month, year);
    	if(day > lastDayOfMonth){
    		day = lastDayOfMonth;
    	}
    	return day;
    }

    /**
     * Creates a NEW date by adding the specified number of days to the base 
     * date.
     * It operates on variables of DayDate, should not be static.
     * @param days  the number of days to add (can be negative).
     * @param base  the base date.
     *
     * @return a new date.
     */
    public DayDate plusDays(int days) {
    	return DayDateFactory.makeDate(getOrdinalDay() + days);
    }

    /**
     * Creates a new date by adding the specified number of months to the base 
     * date.
     * <P>
     * If the base date is close to the end of the month, the day on the result
     * may be adjusted slightly:  31 May + 1 month = 30 June.
     *
     * @param months  the number of months to add (can be negative).
     * @param base  the base date.
     *
     * @return a new date.
     */
    public DayDate plusMonths(int months) {
    	int thisMonthAsOrdinal = getMonth().toInt() - Month.JANUARY.toInt();
    	int thisMonthAndYearAsOrdinal = 12 * getYear() + thisMonthAsOrdinal;
    	int resultMonthAndYearAsOrdinal = thisMonthAndYearAsOrdinal + months;
    	// ...
    	
    	int thisMonthAsOrdinal = 12 * getYear() + getMonth().index - 1;
    	int resultMonthAsOrdinal = thisMonthAsOrdinal + months;
    	
    	int resultYear = resultMonthAsOrdinal / 12;
    	Month resultMonth = Month.fromInt(resultMonthAsOrdinal % 12 + 1);
    	
    	int lastDayOfResultMonth = DateUtil.lastDayOfMonth(resultMonth, resultYear);
    	int resultDay = Math.min(getDayOfMonth(), lastDayOfResultMonth);
    	
    	return DayDateFactory.makeDate(resultDay, resultMonth, resultYear);
    }

    /**
     * Creates a new date by adding the specified number of years to the base 
     * date.
     *
     * @param years  the number of years to add (can be negative).
     * @param base  the base date.
     *
     * @return A new date.
     */
    public DayDate plusYears(int years) {
    	int resultYear = getYear() + years;
    	
    	int lastDayOfMonthInResultYear = DateUtil.lastDayOfMonth(getMonth(), resultYear);
    	int resultDay = Math.min(getDayOfMonth(), lastDayOfMonthInResultYear);
    	return DayDateFactory.makeDate(resultDay, getMonth(), resultYear);    	
    }

    /**
     * Returns the latest date that falls on the specified day-of-the-week and 
     * is BEFORE the base date.
     *
     * @param targetWeekday  a code for the target day-of-the-week.
     * @param base  the base date.
     *
     * @return the latest date that falls on the specified day-of-the-week and 
     *         is BEFORE the base date.
     */
    public DayDate getPreviousDayOfWeek(Day targetDayOfWeekday) {
    	int offsetToTarget = targetDayOfWeekday.toInt() - getDayOfWeek().toInt();
    	if(offsetToTarget >= 0){
    		offsetToTarget = offsetToTarget - 7;
    	}
    	return plusDays(offsetToTarget);    	
    }

    /**
     * Returns the earliest date that falls on the specified day-of-the-week
     * and is AFTER the base date.
     * 获得下一个周几的 SerialDate 对象.
     * @param targetWeekday  a code for the target day-of-the-week.
     * @param base  the base date.
     *
     * @return the earliest date that falls on the specified day-of-the-week 
     *         and is AFTER the base date.
     */
    public DayDate getFollowingDayOfWeek(Day targetDayOfWeek) {
    	int offsetToTarget = targetDayOfWeek.toInt() - getDayOfWeek().toInt();
    	if(offsetToTarget <= 0){
    		offsetToTarget = offsetToTarget + 7;
    	}
    	return plusDays(offsetToTarget);    	
    }

    /**
     * Returns the date that falls on the specified day-of-the-week and is
     * CLOSEST to the base date.
     *
     * @param targetDOW  a code for the target day-of-the-week.
     * @param base  the base date.
     *
     * @return the date that falls on the specified day-of-the-week and is 
     *         CLOSEST to the base date.
     */
    public DayDate getNearestDayOfWeek(Day targetDay) {
    	int offsetToThisWeeksTarget = targetDay.toInt() - getDayOfWeek().toInt();
    	int offsetToFutureTarget = (offsetToThisWeeksTarget + 7) % 7;
    	int offsetToPreviousTarget = offsetToFutureTarget - 7;
    	
    	if(offsetToFutureTarget > 3){
    		return plusDays(offsetToPreviousTarget);
    	}else {
    		return plusDays(offsetToFutureTarget);
    	}
    }

    /**
     * Rolls the date forward to the last day of the month.
     *
     * @param base  the base date.
     *
     * @return a new serial date.
     */
    public DayDate getEndOfMonth() {
    	Month month = getMonth();
    	int year = getYear();
    	int lastDay = DateUtil.lastDayOfMonth(month, year);
    	return DayDateFactory.makeDate(lastDay, month, year);    	
    }    

    /**
     * Returns the serial number for the date, where 1 January 1900 = 2 (this
     * corresponds, almost, to the numbering system used in Microsoft Excel for
     * Windows and Lotus 1-2-3).
     *
     * @return the serial number for the date.
     */
    public abstract int getOrdinalDay();

    /**
     * Returns a java.util.Date.  Since java.util.Date has more precision than
     * SerialDate, we need to define a convention for the 'time of day'.
     *
     * @return this as <code>java.util.Date</code>.
     */
    public java.util.Date toDate(){
        final Calendar calendar = Calendar.getInstance();
        int ordinalMonth = getMonth().toInt() - Month.JANUARY.toInt();
        calendar.set(getYear(), ordinalMonth, getDayOfMonth(), 0, 0, 0);        
        return calendar.getTime();
    }

    /**
     * Converts the date to a string.
     *
     * @return  a string representation of the date.
     */
    public String toString() {
    	return String.format("%02d-%s-%d", getDayOfMonth(), getMonth(), getYear());
//        return getDayOfMonth() + "-" + DayDate.monthCodeToString(getMonth())
//                               + "-" + getYear();
    }

    /**
     * Returns the year (assume a valid range of 1900 to 9999).
     *
     * @return the year.
     */
    public abstract int getYear();

    /**
     * Returns the month (January = 1, February = 2, March = 3).
     *
     * @return the month of the year.
     */
    public abstract Month getMonth();

    /**
     * Returns the day of the month.
     *
     * @return the day of the month.
     */
    public abstract int getDayOfMonth();

    /**
     *
     */
    public Day getDayOfWeek(){
    	Day startingDay = getDayOfWeekForOrdinalZero();
    	int startingOffset = startingDay.toInt() - Day.SUNDAY.toInt();
    	return Day.fromInt((getOrdinalDay() + startingOffset) % 7 + 1);
    }
    
    abstract Day getDayOfWeekForOrdinalZero();

    /**
     * Returns the difference (in days) between this date and the specified 
     * 'other' date.
     * <P>
     * The result is positive if this date is after the 'other' date and
     * negative if it is before the 'other' date.
     *
     * @param other  the date being compared to.
     *
     * @return the difference between this and the other date.
     */
    public int daysSince(DayDate date){
    	return getOrdinalDay() - date.getOrdinalDay();    	
    }

    /**
     * Returns true if this SerialDate represents the same date as the 
     * specified SerialDate.
     *
     * @param other  the date being compared to.
     *
     * @return <code>true</code> if this SerialDate represents the same date as 
     *         the specified SerialDate.
     */
    public abstract boolean isOn(DayDate other);

    /**
     * Returns true if this SerialDate represents an earlier date compared to
     * the specified SerialDate.
     *
     * @param other  The date being compared to.
     *
     * @return <code>true</code> if this SerialDate represents an earlier date 
     *         compared to the specified SerialDate.
     */
    public abstract boolean isBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the 
     * specified SerialDate.
     *
     * @param other  the date being compared to.
     *
     * @return <code>true<code> if this SerialDate represents the same date
     *         as the specified SerialDate.
     */
    public abstract boolean isOnOrBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the 
     * specified SerialDate.
     *
     * @param other  the date being compared to.
     *
     * @return <code>true</code> if this SerialDate represents the same date
     *         as the specified SerialDate.
     */
    public abstract boolean isAfter(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the 
     * specified SerialDate.
     *
     * @param other  the date being compared to.
     *
     * @return <code>true</code> if this SerialDate represents the same date
     *         as the specified SerialDate.
     */
    public abstract boolean isOnOrAfter(DayDate other);

    /**
     * Returns <code>true</code> if this {@link DayDate} is within the 
     * specified range (INCLUSIVE).  The date order of d1 and d2 is not 
     * important.
     *
     * @param d1  a boundary date for the range.
     * @param d2  the other boundary date for the range.
     *
     * @return A boolean.
     */
    public abstract boolean isInRange(DayDate d1, DayDate d2);

    /**
     * Returns <code>true</code> if this {@link DayDate} is within the 
     * specified range (caller specifies whether or not the end-points are 
     * included).  The date order of d1 and d2 is not important.
     *
     * @param d1  a boundary date for the range.
     * @param d2  the other boundary date for the range.
     * @param include  a code that controls whether or not the start and end 
     *                 dates are included in the range.
     *
     * @return A boolean.
     */
    public boolean isInRange(DayDate d1, DayDate d2, DateInterval interval){
    	int left = Math.min(d1.getOrdinalDay(), d2.getOrdinalDay());
    	int right = Math.max(d1.getOrdinalDay(), d2.getOrdinalDay());
    	return interval.isIn(getOrdinalDay(), left, right);
    }



}
