package de.tbosch.tools.timetool.exception;

public class JiraBusinessException extends AbstractBusinessException {

	private static final long serialVersionUID = 1L;

	public static final String URL_MALFORMED = "de.tbosch.tools.service.url_incorrect";

	public static final String CONNCETION_FAILURE = "de.tbosch.tools.service.connection_error";

	/**
	 * @see AbstractBusinessException#AbstractBusinessException()
	 */
	public JiraBusinessException() {
		super();
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String, Throwable)
	 * @param message The message
	 * @param cause The cause Exception
	 * @param params The parameters passed
	 */
	public JiraBusinessException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String)
	 * @param message The message
	 * @param params The parameters passed
	 */
	public JiraBusinessException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(Throwable)
	 * @param cause The cause Exception
	 */
	public JiraBusinessException(Throwable cause) {
		super(cause);
	}

}
