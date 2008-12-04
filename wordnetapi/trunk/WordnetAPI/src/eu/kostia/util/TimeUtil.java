package eu.kostia.util;

import java.text.*;
import java.util.*;

public class TimeUtil {
	static final long DAY_IN_MS = 60 * 60 * 24 * 1000;

	/**
	 * Format a time in millisecond as days HH:mm:ss
	 * 
	 * @param ms
	 *            The amount in milliseconds
	 * @return The formatted amount
	 */
	static public String formatMilliseconds(long ms) {
		long days = ms / DAY_IN_MS;

		long temp = new GregorianCalendar(2000, Calendar.JANUARY, 1)
				.getTimeInMillis();
		Date dummyDate = new Date(temp + ms);

		DateFormat df = new SimpleDateFormat("HH:mm:ss");

		String time = df.format(dummyDate);
		String result = days > 0 ? days + "d " + time : time;

		return result;
	}
}
