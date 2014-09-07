package de.tbosch.tools.timetool.service;

import java.time.LocalDateTime;
import java.util.List;

import com.atlassian.jira.rest.client.domain.input.WorklogInput;

import de.tbosch.tools.timetool.exception.JiraBusinessException;
import de.tbosch.tools.timetool.model.JiraSettings;

/**
 * Service for connection to Jira services.
 * 
 * @author Thomas Bosch
 */
public interface JiraService {

	/**
	 * Sends a worklog to the the Jira server.
	 * 
	 * @param name The name of the ticket
	 * @param startDate The start date
	 * @param minutesSpent The time spent on ticket
	 * @param comment The comment
	 * @param settings The Jira setting with URL, username and so on
	 * @return The created worklog
	 * @throws JiraBusinessException if something went wrong
	 */
	WorklogInput addWorklog(String name, LocalDateTime startDate, long minutesSpent, String comment, JiraSettings settings);

	/**
	 * Gets the description of the given ticket name.
	 * 
	 * @param name The name
	 * @param settings The Jira setting like URL, username and so on
	 * @return The description
	 * @throws JiraBusinessException if something went wrong
	 */
	String getSummary(String name, JiraSettings settings);

	/**
	 * Get the ticket names by a search of the given string.
	 * 
	 * @param text The text to search for
	 * @param settings The Jira settings like URL, username and so on
	 * @return List of ticket names
	 * @throws JiraBusinessException if something went wrong
	 */
	List<String> getTicketNamesBySearchText(String text, JiraSettings settings);

}
