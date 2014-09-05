package de.tbosch.tools.timetool.exception;

/**
 * Exceptions in service layer.
 * 
 * @author Thomas Bosch
 */
public class ServiceBusinessException extends AbstractBusinessException {

	private static final long serialVersionUID = 1L;

	public static final String TOO_MUCH_TIME_SINCE_LAST_UPDATE = "de.tbosch.tools.service.too_much_time";

	public static final String ENDTIME_BEFORE_STARTTIME = "de.tbosch.tools.service.end_before_start";

	public static final String STARTTIME_AFTER_ENDTIME = "de.tbosch.tools.service.start_after_end";

	public static final String TIMESLOT_ALREADY_SENT = "de.tbosch.tools.service.already_sent";

	public static final String DIFFERENT_DATES_IN_LIST = "de.tbosch.tools.service.different_dates";

	public static final String ADDING_OF_WORKLOG_FAILED = "de.tbosch.tools.service.adding_failed";

	public static final String URL_MALFORMED = "de.tbosch.tools.service.url_incorrect";

	public static final String CONNCETION_FAILURE = "de.tbosch.tools.service.connection_error";

	public static final String SOAP_CLIENT_ERROR = "de.tbosch.tools.service.soap_client_error";

	public static final String NO_ACTIVE_PROJECT_FOUND = "de.tbosch.tools.service.no_active_project_found";

	public static final String TICKET_NOT_FOUND = "de.tbosch.tools.service.no_ticket_found";

	public static final String TICKET_NOT_FOUND_IN_SEARCH = "de.tbosch.tools.service.no_ticket_found_in_search";

	/**
	 * @see AbstractBusinessException#AbstractBusinessException()
	 */
	public ServiceBusinessException() {
		super();
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String, Throwable)
	 * @param message The message
	 * @param cause The cause Exception
	 * @param params The parameters passed
	 */
	public ServiceBusinessException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String)
	 * @param message The message
	 * @param params The parameters passed
	 */
	public ServiceBusinessException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(Throwable)
	 * @param cause The cause Exception
	 */
	public ServiceBusinessException(Throwable cause) {
		super(cause);
	}

}
