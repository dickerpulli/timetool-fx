package de.tbosch.tools.timetool.exception;

/**
 * Exception for problems in utility classes.
 * 
 * @author Thomas Bosch
 */
public class UtilsBusinessException extends AbstractBusinessException {

	private static final long serialVersionUID = 1L;

	public static final String WRITE_ERROR = "de.tbosch.tools.utils.write_error";

	public static final String IO_ERROR = "de.tbosch.tools.utils.io_error";

	public static final String EXCEL_ERROR = "de.tbosch.tools.utils.excel_error";

	public static final String PARSE_ERROR = "de.tbosch.tools.utils.parse_error";

	public static final String NOT_SAME_DAY = "de.tbosch.tools.utils.not_same_day";

	public static final String TOO_MUCH_TIMESLOTS_FOR_DAY = "de.tbosch.tools.utils.too_much_timslots";

	/**
	 * @see AbstractBusinessException#AbstractBusinessException()
	 */
	public UtilsBusinessException() {
		super();
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String, Throwable))
	 * @param message The message
	 * @param cause The cause Exception
	 * @param params The parameters passed
	 */
	public UtilsBusinessException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String))
	 * @param message The message
	 * @param params The parameters passed
	 */
	public UtilsBusinessException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(Throwable))
	 * @param cause The cause Exception
	 */
	public UtilsBusinessException(Throwable cause) {
		super(cause);
	}

}
