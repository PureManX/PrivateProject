package kr.cnkisoft.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PureMaN on 2017-08-31.
 */
public class DateUtils {

	public static String formattedDateString(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		return simpleDateFormat.format(date);
	}

	public static String currentDateOfYear() {
		return DateUtils.formattedDateString(new Date(), "yyyyMMdd");
	}
}
