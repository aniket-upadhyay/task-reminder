package com.aniket.taskreminder.util;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtils {
	private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);
	
	public static Date currentDatetime = new Date();

	
	public static LocalDate currentDate = LocalDate.now();
	
	public static Date getDateWithZeroTime(Date date) {
		Date currentDate = new Date();
		String strDate = convertDateToString(date, "yyyy-MM-dd 00:00:00");

		currentDate = convertStringToDate(strDate, "yyyy-MM-dd 00:00:00");
		
		return currentDate;
	}
	
	public static String convertDateToString(Date date, String format) {
		
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		String strDate = "";
		try {
			strDate = dateFormat.format(date.getTime());
		}catch(Exception e) {
			
		}
		return strDate;
	}
	
	public static Date convertStringToDate(String strDate, String format) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US); 
		
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
			
		}catch(Exception e) {
			
		}
		return date;
	}
	
	public static DateTime convertStringToDateTime(String strDate, String format) {
		
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		DateTime dateTime = null;
		try {
			Date date = convertStringToDate(strDate, format);
			dateTime = new DateTime(date.getTime());
		}catch(Exception e) {
			
		}
		return dateTime;
	}
	

}
