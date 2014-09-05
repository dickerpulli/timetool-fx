package de.tbosch.tools.timetool.dao;

import de.tbosch.tools.timetool.model.Timeslot;

/**
 * DAO interface to access {@link Timeslot} objects.
 * 
 * @author Thomas Bosch*
 */
public interface TimeslotDao extends GenericDao<Timeslot, Long> {

	/**
	 * Finds the actual active timeslot - means, with NULL value of endtime.
	 * 
	 * @return The timeslot
	 */
	Timeslot findActive();

	/**
	 * Finds a timeslot, that has the a starttime today. If more than one timeslot exist for today, then get the latest one.
	 * 
	 * @param projectId The project to search in
	 * @return The timeslot
	 */
	Timeslot findWithTodayStarttime(long projectId);

}
