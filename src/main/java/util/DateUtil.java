package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	// util.Date -> sql.Date
	public static java.sql.Date convertSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	public static Date convertDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static String convertString(Date date) {
		String s = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		s = sdf.format(date);
		return s;
	}
}
