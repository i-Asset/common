/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.nimble.utility;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yildiray
 */
public class DateUtility {

	private static final String FORMAT_INPUT = "dd-MM-yyyy";
	private static final String FORMAT_DB_OUTPUT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

	public static Logger log = LoggerFactory.getLogger(DateUtility.class);

//	private static DateTimeFormatter[] formatters = {
//		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"),
//		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
//		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ"),
//		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ"),
//		DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")
//	};
//	public static String convert(DateTime dateTime, String format) {
//		DateTimeFormatter patternFormat = DateTimeFormat.forPattern(format);
//		return dateTime.toString(patternFormat);
//	}
//
//	public static DateTime convert(String dateTime) {
//		for (DateTimeFormatter dateTimeFormatter : formatters) {
//			try {
//				return dateTimeFormatter.parseDateTime(dateTime);
//			} catch (IllegalArgumentException e) {
//				// ignore
//			}
//		}
//
//		throw new IllegalArgumentException("Could not parse: " + dateTime);
//	}
//
//	public static XMLGregorianCalendar convertStringToGregorian(String dateString) {
//		try {
//			DateTime dateTime = convert(dateString);
//			GregorianCalendar calendar = new GregorianCalendar(dateTime.getZone().toTimeZone());
//			calendar.setTimeInMillis(dateTime.getMillis());
//			return DatatypeFactory.newInstance()
//				.newXMLGregorianCalendar(calendar);
//		} catch (DatatypeConfigurationException ex) {
//			log.error("", ex);
//		}
//		return null;
//	}
//
//	public static DateTime convert(XMLGregorianCalendar calendar) {
//		return new DateTime(calendar.toGregorianCalendar().getTime());
//	}
//
//	public static String convertGregorianToString(XMLGregorianCalendar calendar) {
//		return convert(new DateTime(calendar.toGregorianCalendar().getTime()));
//	}

	public static String getCurrentTimeStamp() {
		DateTime dt = DateTime.now();
		DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
		return dt.toString(formatter);
	}

	public static String convert(DateTime dateTime) {
		DateTimeFormatter patternFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		return dateTime.toString(patternFormat);
	}

	public static boolean isValidDate(String inputDate) {
		DateTimeFormatter inputFormat = DateTimeFormat.forPattern(FORMAT_INPUT);
		try {
			inputFormat.parseDateTime(inputDate);
			return true;
		} catch (Exception e) {
			log.warn("Failed to parse input date: {}", inputDate);
			return false;
		}
	}

	public static String transformInputDateToDbDate(String inputDate) {
		DateTimeFormatter inputFormat = DateTimeFormat.forPattern(FORMAT_INPUT);
		DateTimeFormatter dbFormat = DateTimeFormat.forPattern(FORMAT_DB_OUTPUT);

		DateTime date = inputFormat.parseDateTime(inputDate);
		return dbFormat.print(date);
	}

	public static String transformInputDateToMaxDbDate(String inputDate) {
		DateTimeFormatter inputFormat = DateTimeFormat.forPattern(FORMAT_INPUT);
		DateTimeFormatter dbFormat = DateTimeFormat.forPattern(FORMAT_DB_OUTPUT);

		DateTime date = inputFormat.parseDateTime(inputDate);
		date = date.plusDays(1).minusMillis(1);
		return dbFormat.print(date);
	}
}
