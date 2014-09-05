package de.tbosch.tools.timetool.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateUtilsTest {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS");

	@Test
	public void testGetPeriodAsString() throws ParseException {
		Date date1 = sdf.parse("01.01.2009 00:00:00,000");
		Date date2 = sdf.parse("01.01.2009 01:00:00,000");
		String diff = DateUtils.getDifferenceAsString(date1, date2, false, true);
		assertEquals("0d 1h 0m 0s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 01:02:03,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, false, true);
		assertEquals("0d 1h 2m 3s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("02.01.2009 01:02:03,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, false, true);
		assertEquals("1d 1h 2m 3s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 01:00:00,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("1h 0m 0s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 00:01:00,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("1m 0s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 01:00:00,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("1h 0m 0s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 00:00:01,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("1s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 01:00:01,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("1h 0m 1s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 00:00:00,900");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("900 millis", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 00:00:00,900");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, false);
		assertEquals("0m", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 08:59:59,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, true);
		assertEquals("8h 59m 59s", diff);

		date1 = sdf.parse("01.01.2009 00:00:00,000");
		date2 = sdf.parse("01.01.2009 08:59:59,000");
		diff = DateUtils.getDifferenceAsString(date1, date2, true, false);
		assertEquals("8h 59m", diff);
	}

	@Test
	public void testGetNameOfMonth() {
		Locale.setDefault(Locale.GERMANY);
		assertEquals("Januar", DateUtils.getNameOfMonth(1));
		assertEquals("Dezember", DateUtils.getNameOfMonth(12));
	}

	@Test
	public void testGetDifferenceInMinutes() throws ParseException {
		Date from = sdf.parse("01.01.2010 00:00:00,000");
		Date to = sdf.parse("01.01.2010 00:00:59,999");
		int minutes = DateUtils.getDifferenceInMinutes(from, to);
		assertEquals(0, minutes);

		from = sdf.parse("01.01.2010 00:00:00,000");
		to = sdf.parse("01.01.2010 00:01:00,000");
		minutes = DateUtils.getDifferenceInMinutes(from, to);
		assertEquals(1, minutes);

		from = sdf.parse("01.01.2010 00:00:00,000");
		to = sdf.parse("01.01.2010 01:01:00,000");
		minutes = DateUtils.getDifferenceInMinutes(from, to);
		assertEquals(61, minutes);
	}

	@Test
	public void roundUpToSeconds() throws ParseException {
		Date date = sdf.parse("01.01.2010 00:00:00,000");
		Date round = DateUtils.roundUpToSeconds(date);
		assertEquals("01.01.2010 00:00:00,000", sdf.format(round));

		date = sdf.parse("01.01.2010 00:00:00,001");
		round = DateUtils.roundUpToSeconds(date);
		assertEquals("01.01.2010 00:00:01,000", sdf.format(round));

		date = sdf.parse("01.01.2010 00:00:00,999");
		round = DateUtils.roundUpToSeconds(date);
		assertEquals("01.01.2010 00:00:01,000", sdf.format(round));
	}

}
