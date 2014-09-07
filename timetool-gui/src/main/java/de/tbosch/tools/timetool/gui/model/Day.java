package de.tbosch.tools.timetool.gui.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.DateUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils;

/**
 * View class to display a summary of timeslots per day.
 * 
 * @author Thomas Bosch
 */
public class Day {

	private final List<Timeslot> timeslots;

	private final LocalDate day;

	/**
	 * Constructor.
	 * 
	 * @param timeslots The timeslots that are related to this day
	 */
	public Day(List<Timeslot> timeslots) {
		if (CollectionUtils.isEmpty(timeslots)) {
			throw new IllegalArgumentException("timeslots should not be empty");
		}
		Set<LocalDate> starttimeDays = new HashSet<LocalDate>();
		for (Timeslot timeslot : timeslots) {
			starttimeDays.add(LocalDateTime.ofInstant(timeslot.getStarttime().toInstant(), ZoneId.systemDefault()).toLocalDate());
		}
		if (starttimeDays.size() > 1) {
			throw new IllegalArgumentException("timeslots should be in one day");
		}
		this.timeslots = timeslots;
		this.day = starttimeDays.iterator().next();
	}

	/**
	 * Checks if all included timeslots are marked.
	 * 
	 * @return <code>true</code> if all are marked
	 */
	public boolean isAllMarked() {
		for (Timeslot timeslot : timeslots) {
			if (!timeslot.isMarked()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		Duration sum = TimeslotUtils.getSum(timeslots);
		String summary = DateUtils.getDurationAsString(sum);
		Collections.sort(timeslots);
		String theDay = DateUtils.toDateString(day);
		return theDay + " - " + summary;
	}

	// Getter / Setter

	/**
	 * @return The local date
	 */
	public LocalDate getLocalDate() {
		return day;
	}

	/**
	 * @return the timeslots
	 */
	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

}
