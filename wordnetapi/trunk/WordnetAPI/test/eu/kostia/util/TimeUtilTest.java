package eu.kostia.util;

import static org.junit.Assert.*;

import org.junit.*;

public class TimeUtilTest {

	@Test
	public void testFormatMilliseconds00() {
		long ms = 60 * 60 * 24 * 1000 - 1000;
		String actual = TimeUtil.formatMilliseconds(ms);
		String expected = "23:59:59";
		assertEquals(expected, actual);
	}

	@Test
	public void testFormatMilliseconds01() {
		long ms = 60 * 60 * 24 * 1000 / 2;
		String actual = TimeUtil.formatMilliseconds(ms);
		String expected = "12:00:00";
		assertEquals(expected, actual);
	}

	@Test
	public void testFormatMilliseconds02() {
		long ms = 60 * 60 * 24 * 1000 / 2;
		String actual = TimeUtil.formatMilliseconds(3 * ms);
		String expected = "1d 12:00:00";
		assertEquals(expected, actual);
	}

}
