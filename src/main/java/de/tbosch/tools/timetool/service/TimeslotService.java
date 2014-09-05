package de.tbosch.tools.timetool.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import de.tbosch.tools.timetool.exception.ServiceBusinessException;
import de.tbosch.tools.timetool.model.JiraSettings;
import de.tbosch.tools.timetool.model.Timeslot;

/**
 * Service for accessing timeslot entities.
 * 
 * @author Thomas Bosch
 */
public interface TimeslotService {

	/**
	 * Gets all known timeslots.
	 * 
	 * @return The list of all timeslots
	 */
	List<Timeslot> getAllTimeslots();

	/**
	 * Gets the timeslot with the given id.
	 * 
	 * @param timeslotId The id of the timeslot
	 * @return The timeslot
	 */
	Timeslot getTimeslot(long timeslotId);

	/**
	 * Starts a timeslot in the database. Only, if no active one exists.
	 * 
	 * @param projectId The associated projects id
	 */
	void activateTimeslot(long projectId);

	/**
	 * Ends the given timeslot. The current time is the endtime.
	 * 
	 * @param timeslotId The timeslots id
	 */
	void deactivateTimeslot(long timeslotId);

	/**
	 * Gets the active timeslot. Indicated on NULL value of endtime.
	 * 
	 * @return The timeslot
	 */
	Timeslot getActiveTimeslot();

	/**
	 * Return a map of timeslot liste with key: project name.
	 * 
	 * @return The map
	 */
	Map<String, List<Timeslot>> getAllTimeslotsPerProject();

	/**
	 * Updates the endtime of the timeslot to the actual time.
	 * 
	 * @param timeslot The timeslot to update
	 * @return The updated object
	 */
	Timeslot updateEndtime(Timeslot timeslot);

	/**
	 * Deactivates the timeslot marked as active, if one exists.
	 */
	void deactivateActiveTimeslot();

	/**
	 * Deletes the timeslot.
	 * 
	 * @param timeslot The timeslot to delete
	 */
	void deleteTimeslot(Timeslot timeslot);

	/**
	 * Sets the starttime of the timeslot.
	 * 
	 * @param timeslot The timeslot to set
	 * @param starttime The date
	 */
	void setStarttime(Timeslot timeslot, Date starttime);

	/**
	 * Sets the endtime of the timeslot.
	 * 
	 * @param timeslot The timeslot to set
	 * @param endtime The date
	 */
	void setEndtime(Timeslot timeslot, Date endtime);

	/**
	 * Sums the time of alle timeslots and formats it in a String.
	 * 
	 * @param timeslots The List
	 * @return The formatted String
	 */
	String addTimeslots(List<Timeslot> timeslots);

	/**
	 * Set all timeslots to the reverse value of the actual marked value.
	 * 
	 * @param timeslots The list
	 */
	void toggleMarking(List<Timeslot> timeslots);

	/**
	 * Sends multiple timeslot in sum to Jira ticketing server. Creates worklog items in Jira.
	 * 
	 * @param timeslots The timeslots to send
	 * @param ticketname The unique ticket name
	 * @param comments Some comments to save with it
	 * @param settings The Jira setting with url, username and so on
	 */
	void sendTimeslots(List<Timeslot> timeslots, String ticketname, String comments, JiraSettings settings);

	/**
	 * Checks if given timeslots are valid to be sent.
	 * 
	 * @param timeslots The list of tomeslots
	 * @throws ServiceBusinessException if validation fails
	 */
	void validateForSending(List<Timeslot> timeslots);

	/**
	 * Starts/Activate a timeslot if none is active or stop if one exists.
	 */
	void toggleTimeslotActivation();

	/**
	 * Saves the new starttime and endtime to the timeslot.
	 * 
	 * @param timeslot The timeslot
	 * @param starttime The starttime
	 * @param endtime The endtime
	 */
	void saveTimeslot(Timeslot timeslot, Date starttime, Date endtime);

}
