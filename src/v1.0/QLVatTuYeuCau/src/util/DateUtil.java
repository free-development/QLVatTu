/**
 * 
 */
package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtil.
 *
 * @author QUOI
 */
public final class DateUtil {
	
	/**
	 * Instantiates a new date util.
	 */
	private DateUtil() {
	}
	
	/**
	 * Convert to sql date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	@SuppressWarnings("deprecation")
	public static Date convertToSqlDate(final java.util.Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate());
	}
//	public static S to(final java.util.Date date) {
//		return new Date(date.getYear(), date.getMonth(), date.getDate());
//	}
	// public static DateUtil nowDateSql() {
	// java.util.Date date = new java.util.Date();
	// return ()
	// }
	/**
	 * Parses the date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static final String toString(final java.sql.Date date)
	{
		String d = date.getDate()+"/"+ (date.getMonth() + 1) +"/" + (date.getYear() + 1900);
		return d;
	}
	public static final String toString(final java.util.Date date)
	{
		String d = date.getDate()+"/"+ (date.getMonth() + 1) +"/" + (date.getYear() + 1900);
		return d;
	}
	
	public static Date parseDate(final String date) {
		Date d = null;
		try {
		d = convertToSqlDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			System.out
					.println("Can't not convert from string to java.sql.Date");
		}
		return d;
	}
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String [ ] args) {
		String t = "1994-03-26";
		
		System.out.println(parseDate(t));
	}
}
