package de.tbosch.tools.timetool.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Utilities for date objects.
 * 
 * @author Thomas Bosch
 */
public final class DateUtils {

	/**
	 * Utils.
	 */
	private DateUtils() {
		// Utils
	}

	/**
	 * If the milliseconds field of the date time is more then zero, then set the milliseconds to zero and add one second to the date instance and give it back.
	 * 
	 * @param date The date time to round up
	 * @return The rounded value
	 */
	public static Date roundUpToSeconds(Date date) {
		LocalDateTime localDateTime = new LocalDateTime(date.getTime());
		int millis = localDateTime.getMillisOfSecond();
		if (millis > 0) {
			localDateTime = localDateTime.minusMillis(millis);
			localDateTime = localDateTime.plusSeconds(1);
		}
		return localDateTime.toDateTime().toDate();
	}

	/**
	 * Return the difference between two dates as a text.<br/>
	 * It will look like: <code>26h 12m 56s</code> If the difference is smaller than 1 second,the number of milliseconds will be shown like:
	 * <code>568 millis</code>
	 * 
	 * @param starttime The start
	 * @param endtime The end
	 * @param deleteZeros Whether to delete leading zeros
	 * @return The text
	 */
	public static String getDifferenceAsString(Date starttime, Date endtime, boolean deleteZeros) {
		return getDifferenceAsString(starttime, endtime, deleteZeros, false);
	}

	/**
	 * Return the difference between two dates as a text.<br/>
	 * It will look like: <code>26h 12m 56s</code> If the difference is smaller than 1 second,the number of milliseconds will be shown like:
	 * <code>568 millis</code>
	 * 
	 * @param starttime The start
	 * @param endtime The end
	 * @param deleteZeros Whether to delete leading zeros
	 * @param detailed Lists also seconds and milliseconds
	 * @return The text
	 */
	public static String getDifferenceAsString(Date starttime, Date endtime, boolean deleteZeros, boolean detailed) {
		return getPeriodAsString(new Interval(starttime.getTime(), endtime.getTime()).toDuration().toPeriod(), deleteZeros, detailed);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code>. The precision is days. The period will be normalized. No details will be printed
	 * 
	 * @param period A period
	 * @param deleteZeros Whether to delete leading zeros
	 * @return The text
	 */
	public static String getPeriodAsString(Period period, boolean deleteZeros) {
		return getPeriodAsString(period, deleteZeros, false);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code>. The precision is days. The period will be normalized. No details will be printed. Leading zeros will be
	 * dropped.
	 * 
	 * @param period A period
	 * @return The text
	 */
	public static String getPeriodAsString(Period period) {
		return getPeriodAsString(period, true);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code> If the difference is smaller than 1 second,the number of milliseconds will be shown like:
	 * <code>568 millis</code>. The precision is days. The period will be normalized.
	 * 
	 * @param period A period
	 * @param deleteZeros Whether to delete leading zeros
	 * @param detailed Lists also seconds and milliseconds
	 * @return The text
	 */
	public static String getPeriodAsString(Period period, boolean deleteZeros, boolean detailed) {
		period = period.normalizedStandard();
		long millis = period.getMillis();
		long seconds = period.getSeconds();
		long minutes = period.getMinutes();
		long hours = period.getHours();
		long days = period.getDays();
		if (deleteZeros) {
			String text = "";
			if (days > 0) {
				text += days + "d ";
			}
			if (days > 0 || hours > 0) {
				text += hours + "h ";
			}
			if (days > 0 || hours > 0 || minutes > 0) {
				text += minutes + "m";
			}
			if (detailed) {
				if (StringUtils.isNotEmpty(text)) {
					text += " ";
				}
				if (days > 0 || hours > 0 || minutes > 0 || seconds > 0) {
					text += seconds + "s";
				}
				if (days <= 0 && hours <= 0 && minutes <= 0 && seconds <= 0) {
					text += millis + " millis";
				}
			} else {
				if (StringUtils.isEmpty(text)) {
					return "0m";
				}
			}
			return text;
		} else {
			if (days <= 0 && hours <= 0 && minutes <= 0 && seconds <= 0) {
				if (detailed) {
					return millis + " millis";
				} else {
					return "0m";
				}
			} else {
				String text = days + "d " + hours + "h " + minutes + "m";
				if (detailed) {
					text += " " + seconds + "s";
				}
				return text;
			}
		}
	}

	/**
	 * Gets the name of the month in the current locale.
	 * 
	 * @param date The date with the month to check
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonthAndYear(Date date) {
		return getNameOfMonthAndYear(new LocalDate(date.getTime()));
	}

	/**
	 * Gets the name of the month in the current locale.
	 * 
	 * @param date The date with the month to check
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonthAndYear(LocalDate date) {
		int month = date.getMonthOfYear();
		int year = date.getYear();
		return getNameOfMonth(month) + " " + year;
	}

	/**
	 * Gets the name of the month in the current locale.
	 * 
	 * @param date The date with the month to check
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonth(Date date) {
		int month = new LocalDate(date).getMonthOfYear();
		return getNameOfMonth(month);
	}

	/**
	 * Gets the name of the month in the current.
	 * 
	 * @param month The month in the year (1...12)
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonth(int month) {
		DateFormat df = new SimpleDateFormat("MMMM", Locale.getDefault());
		Date dateMonth = new LocalDate(1, month, 1).toDateMidnight().toDate();
		return df.format(dateMonth);
	}

	/**
	 * Gets the difference between to date objects in minutes.
	 * 
	 * @param from From date
	 * @param to To date
	 * @return The count of minutes
	 */
	public static int getDifferenceInMinutes(Date from, Date to) {
		Interval interval = new Interval(from.getTime(), to.getTime());
		Period period = interval.toPeriod().normalizedStandard(PeriodType.minutes());
		return period.getMinutes();
	}

	/**
	 * Formats a given date to the localized String representation.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateString(Date date) {
		return toDateString(new LocalDate(date.getTime()));
	}

	/**
	 * Formats a given date to the localized String representation. Display only the day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateString(LocalDate date) {
		SimpleDateFormat sdf = new SimpleDateFormat(MessageHelper.getMessage("format.date"));
		return sdf.format(date.toDateMidnight().toDate());
	}

	/**
	 * Formats a given date to the localized String representation. Display time and day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateTimeString(Date date) {
		return toDateTimeString(new LocalDate(date.getTime()));
	}

	/**
	 * Formats a given date to the localized String representation. Display time and day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateTimeString(LocalDate date) {
		SimpleDateFormat sdf = new SimpleDateFormat(MessageHelper.getMessage("format.timestamp"));
		return sdf.format(date.toDateMidnight().toDate());
	}

	/**
	 * Formats a given date to the localized String representation. Display just time.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toTimeString(Date date) {
		return toTimeString(new DateTime(date.getTime()));
	}

	/**
	 * Formats a given date to the localized String representation. Display just time.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toTimeString(DateTime date) {
		SimpleDateFormat sdf = new SimpleDateFormat(MessageHelper.getMessage("format.time"));
		return sdf.format(date.toDate());
	}

}
