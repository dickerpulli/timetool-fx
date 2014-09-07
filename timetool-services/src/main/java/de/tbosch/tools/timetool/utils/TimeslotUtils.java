package de.tbosch.tools.timetool.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tbosch.tools.timetool.exception.UtilsBusinessException;
import de.tbosch.tools.timetool.model.Timeslot;

/**
 * Utilities for Timeslot-Objects.
 * 
 * @author Thomas Bosch
 */
public final class TimeslotUtils {

	private static final Logger LOG = LoggerFactory.getLogger(TimeslotUtils.class);

	/**
	 * Compares two Interval objects. The one with the smaller start time is the smaller one.
	 */
	private static final Comparator<Interval> INTERVAL_COMPARATOR = new Comparator<Interval>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(Interval o1, Interval o2) {
			return o1.getStart().compareTo(o2.getStart());
		}
	};

	/**
	 * Utils.
	 */
	private TimeslotUtils() {
		// Utils
	}

	/**
	 * Gets the timeslots in a map sorted by month. The month of the starttime is relevant in this section.
	 * 
	 * @param timeslots All timeslots
	 * @return The map with key = month, value = timeslots in month
	 */
	public static Map<Integer, List<Timeslot>> getTimeslotsPerMonth(List<Timeslot> timeslots) {
		Map<Integer, List<Timeslot>> timeslotsPerMonth = new LinkedHashMap<Integer, List<Timeslot>>();
		for (Timeslot timeslot : timeslots) {
			int month = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault()).getMonthValue();
			List<Timeslot> timeslotsInMonth = timeslotsPerMonth.get(month);
			if (timeslotsInMonth == null) {
				timeslotsInMonth = new ArrayList<Timeslot>();
				timeslotsPerMonth.put(month, timeslotsInMonth);
			}
			timeslotsInMonth.add(timeslot);
		}
		return timeslotsPerMonth;
	}

	/**
	 * Gets the timeslots in a map sorted by day. The day of the starttime is relevant in this section.
	 * 
	 * @param timeslots All timeslots
	 * @return The map with key = day, value = timeslots in day
	 */
	public static Map<LocalDate, List<Timeslot>> getTimeslotsPerDay(List<Timeslot> timeslots) {
		Map<LocalDate, List<Timeslot>> timeslotsPerDay = new LinkedHashMap<LocalDate, List<Timeslot>>();
		for (Timeslot timeslot : timeslots) {
			LocalDate day = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault()).toLocalDate();
			List<Timeslot> timeslotsInDay = timeslotsPerDay.get(day);
			if (timeslotsInDay == null) {
				timeslotsInDay = new ArrayList<Timeslot>();
				timeslotsPerDay.put(day, timeslotsInDay);
			}
			timeslotsInDay.add(timeslot);
		}
		return timeslotsPerDay;
	}

	/**
	 * Gets the timeslots in a map sorted my month and day. The month and day of the starttime is relevant in this section.
	 * 
	 * @param timeslots All timeslots
	 * @return The map with key = month, value = map of key = day, value = timeslots in day
	 */
	public static Map<LocalDate, Map<LocalDate, List<Timeslot>>> getTimeslotsPerMonthAndDay(List<Timeslot> timeslots) {
		Map<LocalDate, Map<LocalDate, List<Timeslot>>> timeslotsPerMonthAndDay = new LinkedHashMap<LocalDate, Map<LocalDate, List<Timeslot>>>();
		for (Timeslot timeslot : timeslots) {
			LocalDate month = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1);
			LocalDate day = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault()).toLocalDate();
			Map<LocalDate, List<Timeslot>> timeslotsPerDay = timeslotsPerMonthAndDay.get(month);
			if (timeslotsPerDay == null) {
				timeslotsPerDay = new LinkedHashMap<LocalDate, List<Timeslot>>();
				timeslotsPerMonthAndDay.put(month, timeslotsPerDay);
			}
			List<Timeslot> timeslotsInDay = timeslotsPerDay.get(day);
			if (timeslotsInDay == null) {
				timeslotsInDay = new ArrayList<Timeslot>();
				timeslotsPerDay.put(day, timeslotsInDay);
			}
			timeslotsInDay.add(timeslot);
			timeslotsPerMonthAndDay.put(month, timeslotsPerDay);
		}
		return timeslotsPerMonthAndDay;
	}

	/**
	 * Calculates the sum of the periods of all timeslots.
	 * 
	 * @param timeslots List of timeslots
	 * @return The sum
	 */
	public static Duration getSum(List<Timeslot> timeslots) {
		Duration sum = Duration.ZERO;
		for (Timeslot timeslot : timeslots) {
			Duration period = Duration.between(timeslot.getStarttime().toInstant(), timeslot.getEndtime().toInstant());
			sum = sum.plus(period);
		}
		return sum;
	}

	/**
	 * Converts a Timeslot instance to a Joda-Time Interval.
	 * 
	 * @param timeslot The timeslot
	 * @return The interval
	 */
	public static Interval toInterval(Timeslot timeslot) {
		LocalDateTime starttime = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault());
		LocalDateTime endtime = LocalDateTime.ofInstant(timeslot.getEndtime().toInstant(), ZoneId.systemDefault());
		return new Interval(starttime, endtime);
	}

	/**
	 * Gets interval list. One interval per day. If there are more than one timeslot in one day the durations of the timeslots will be added and the starttime
	 * of the first timeslot will be used.
	 * 
	 * @param timeslots The list of timeslots
	 * @return The intervals
	 */
	public static List<Workday> getWorkdays(List<Timeslot> timeslots) {
		// Create map with a list of intervals per day
		Map<LocalDate, List<Interval>> intervalsPerDay = new HashMap<LocalDate, List<Interval>>();
		for (Timeslot timeslot : timeslots) {
			LocalDateTime starttime = LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault());
			LocalDateTime endtime = LocalDateTime.ofInstant(timeslot.getEndtime().toInstant(), ZoneId.systemDefault());
			LocalDate starttimeDay = starttime.toLocalDate();
			LocalDate endtimeDay = endtime.toLocalDate();
			if (!starttimeDay.equals(endtimeDay)) {
				LogUtils.logError("starttime and endtime must be on the same day", LOG);
				throw new UtilsBusinessException(UtilsBusinessException.NOT_SAME_DAY, starttimeDay, endtimeDay);
			}
			List<Interval> intervalsInDay = intervalsPerDay.get(starttimeDay);
			if (intervalsInDay == null) {
				intervalsInDay = new ArrayList<Interval>();
				intervalsPerDay.put(starttimeDay, intervalsInDay);
			}
			Interval timeslotInterval = toInterval(timeslot);
			intervalsInDay.add(timeslotInterval);
		}
		// Summarize per day
		List<Workday> workdays = new ArrayList<Workday>();
		for (java.util.Map.Entry<LocalDate, List<Interval>> intervalEntry : intervalsPerDay.entrySet()) {
			List<Interval> intervalsInDay = intervalEntry.getValue();
			// Sort, so that the first element is the first in chronological order
			Collections.sort(intervalsInDay, INTERVAL_COMPARATOR);
			Duration durationPauseInDay = Duration.ZERO;
			for (int i = 0; i < intervalsInDay.size() - 1; i++) {
				Interval interval = intervalsInDay.get(i);
				Interval intervalNext = intervalsInDay.get(i + 1);
				Duration pause = Duration.between(interval.getEnd(), intervalNext.getStart());
				durationPauseInDay = durationPauseInDay.plus(pause);
			}
			Interval sumInterval = new Interval(intervalsInDay.get(0).getStart(), intervalsInDay.get(intervalsInDay.size() - 1).getEnd());
			Workday workday = new Workday(sumInterval, durationPauseInDay);
			workdays.add(workday);
		}
		// Sort the result chronological
		Collections.sort(workdays);
		return workdays;
	}

	/**
	 * A container class for a whole workday.
	 * 
	 * @author Thomas Bosch
	 */
	public static class Workday implements Comparable<Workday> {

		/** The interval of worktime, start->end. */
		private final Interval interval;

		/** The sum of pauses of the workday. */
		private final Duration pause;

		/**
		 * Constructor.
		 * 
		 * @param interval Start and end of workday
		 * @param pause The sum of pauses
		 */
		public Workday(Interval interval, Duration pause) {
			this.interval = interval;
			this.pause = pause;
		}

		/**
		 * @return the interval
		 */
		public Interval getInterval() {
			return interval;
		}

		/**
		 * @return the pause
		 */
		public Duration getPause() {
			return pause;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compareTo(Workday o) {
			return this.getInterval().getStart().compareTo(o.getInterval().getStart());
		}

	}

}
