package kr.cnkisoft.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	
}
