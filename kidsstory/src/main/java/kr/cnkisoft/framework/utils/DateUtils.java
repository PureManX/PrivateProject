package kr.cnkisoft.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by PureMaN on 2017-08-31.
 */
public class DateUtils {

	public static String convertStringDateFromString(String dateString, String previousFormat, String convertedFormat) {
		Date convertedDate;
		try {
			convertedDate = new SimpleDateFormat(previousFormat, Locale.KOREAN).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		return new SimpleDateFormat(convertedFormat, Locale.KOREAN).format(convertedDate);
		
	}
	
	public static String formattedDateString(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		return simpleDateFormat.format(date);
	}

	public static String currentDateOfYear() {
		return DateUtils.formattedDateString(new Date(), "yyyyMMdd");
	}
	
	
	public static String getFirstDayOfWeekString() {
		return formattedDateString(getFirstDayOfWeekDate(Calendar.MONDAY), "yyyyMMdd");
	}
	
	public static String getLasttDayOfWeekString() {
		return formattedDateString(getLastDayOfWeekDate(Calendar.FRIDAY), "yyyyMMdd");
	}
	
	public static Date getFirstDayOfWeekDate(int firstDay)
	{
	    Calendar c = new GregorianCalendar();

	    while (c.get(Calendar.DAY_OF_WEEK) != firstDay)
	    {
	        c.add(Calendar.DAY_OF_MONTH, -1);
	    }

	    return c.getTime();
	}
	
	public static Date getLastDayOfWeekDate(int firstDay)
	{
	    Calendar c = new GregorianCalendar();

	    while (c.get(Calendar.DAY_OF_WEEK) != firstDay)
	    {
	        c.add(Calendar.DAY_OF_MONTH, +1);
	    }

	    return c.getTime();
	}
}
