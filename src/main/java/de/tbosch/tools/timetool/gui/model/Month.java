package de.tbosch.tools.timetool.gui.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import de.tbosch.tools.timetool.utils.DateUtils;

/**
 * View class to display a summary of days per month.
 * 
 * @author Thomas Bosch
 */
public class Month {

	private List<Day> days;

	private LocalDate firstOfMonth;

	/**
	 * Constructor.
	 * 
	 * @param firstOfMonth The first day of this month
	 */
	public Month(LocalDate firstOfMonth) {
		this.firstOfMonth = firstOfMonth;
		this.days = new ArrayList<Day>();
	}

	/**
	 * Adds a day to this month.
	 * 
	 * @param day The day to add
	 */
	public void addDay(Day day) {
		days.add(day);
	}

	/**
	 * Checks if all days in month are marked.
	 * 
	 * @return <code>true</code> if all days in this month are marked
	 */
	public boolean isAllMarked() {
		for (Day day : days) {
			if (!day.isAllMarked()) {
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
		String nameOfMonthAndYear = DateUtils.getNameOfMonthAndYear(firstOfMonth);
		return nameOfMonthAndYear;
	}

	// Getter / Setter

	/**
	 * @return The first day
	 */
	public LocalDate getFirstOfMonth() {
		return firstOfMonth;
	}

	/**
	 * @return The days
	 */
	public List<Day> getDays() {
		return days;
	}

}
