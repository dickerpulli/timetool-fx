package de.tbosch.tools.timetool.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

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
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		int nanos = localDateTime.getNano();
		if (nanos > 0) {
			localDateTime = localDateTime.minusNanos(nanos);
			localDateTime = localDateTime.plusSeconds(1);
		}
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
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
		return getDurationAsString(
				Duration.between(LocalDateTime.ofInstant(starttime.toInstant(), ZoneId.systemDefault()),
						LocalDateTime.ofInstant(endtime.toInstant(), ZoneId.systemDefault())), deleteZeros, detailed);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code>. The precision is days. The period will be normalized. No details will be printed
	 * 
	 * @param period A period
	 * @param deleteZeros Whether to delete leading zeros
	 * @return The text
	 */
	public static String getDurationAsString(Duration period, boolean deleteZeros) {
		return getDurationAsString(period, deleteZeros, false);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code>. The precision is days. The period will be normalized. No details will be printed. Leading zeros will be
	 * dropped.
	 * 
	 * @param period A period
	 * @return The text
	 */
	public static String getDurationAsString(Duration duration) {
		return getDurationAsString(duration, true);
	}

	/**
	 * Return the Period as formatted text.<br/>
	 * It will look like: <code>1d 2h 12m 56s</code> If the difference is smaller than 1 second,the number of milliseconds will be shown like:
	 * <code>568 millis</code>. The precision is days. The period will be normalized.
	 * 
	 * @param duration A period
	 * @param deleteZeros Whether to delete leading zeros
	 * @param detailed Lists also seconds and milliseconds
	 * @return The text
	 */
	public static String getDurationAsString(Duration duration, boolean deleteZeros, boolean detailed) {
		long millis = duration.getNano() / 1000000;
		long seconds = duration.getSeconds();
		long minutes = duration.getSeconds() / 60;
		long hours = duration.getSeconds() / (60 * 60);
		long days = duration.getSeconds() / (60 * 60 * 24);
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
		return getNameOfMonthAndYear(LocalDate.from(date.toInstant()));
	}

	/**
	 * Gets the name of the month in the current locale.
	 * 
	 * @param date The date with the month to check
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonthAndYear(LocalDate date) {
		int month = date.getMonthValue();
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
		int month = LocalDate.from(date.toInstant()).getMonthValue();
		return getNameOfMonth(month);
	}

	/**
	 * Gets the name of the month in the current.
	 * 
	 * @param month The month in the year (1...12)
	 * @return The name, i.e. 1 = January
	 */
	public static String getNameOfMonth(int month) {
		LocalDateTime dateMonth = LocalDate.of(1, month, 1).atStartOfDay();
		return dateMonth.format(DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()));
	}

	/**
	 * Gets the difference between to date objects in minutes.
	 * 
	 * @param from From date
	 * @param to To date
	 * @return The count of minutes
	 */
	public static long getDifferenceInMinutes(Date from, Date to) {
		Duration duration = Duration.between(LocalDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault()),
				LocalDateTime.ofInstant(to.toInstant(), ZoneId.systemDefault()));
		return duration.getSeconds() / 60;
	}

	/**
	 * Formats a given date to the localized String representation.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateString(Date date) {
		return toDateString(LocalDate.from(date.toInstant()));
	}

	/**
	 * Formats a given date to the localized String representation. Display only the day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateString(LocalDate date) {
		return date.atStartOfDay().format(DateTimeFormatter.ofPattern(MessageHelper.getMessage("format.date")));
	}

	/**
	 * Formats a given date to the localized String representation. Display time and day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateTimeString(Date date) {
		return toDateTimeString(LocalDate.from(date.toInstant()));
	}

	/**
	 * Formats a given date to the localized String representation. Display time and day.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toDateTimeString(LocalDate date) {
		return date.atStartOfDay().format(DateTimeFormatter.ofPattern(MessageHelper.getMessage("format.timestamp")));
	}

	/**
	 * Formats a given date to the localized String representation. Display just time.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toTimeString(Date date) {
		return toTimeString(LocalDateTime.from(date.toInstant()));
	}

	/**
	 * Formats a given date to the localized String representation. Display just time.
	 * 
	 * @param date The date
	 * @return The text representation
	 */
	public static String toTimeString(LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern(MessageHelper.getMessage("format.time")));
	}

}
