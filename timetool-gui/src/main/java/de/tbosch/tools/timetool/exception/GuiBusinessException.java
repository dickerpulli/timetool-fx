package de.tbosch.tools.timetool.exception;

/**
 * Exception for GUI problems.
 * 
 * @author Thomas Bosch
 */
public class GuiBusinessException extends AbstractBusinessException {

	private static final long serialVersionUID = 1L;

	public static final String TIMESLOT_ALREADY_SENT = "de.tbosch.tools.gui.mark_sent";

	public static final String COMMON_AWT_ERROR = "de.tbosch.tools.gui.common_awt_error";

	public static final String ERROR_WRITING_EXCEL_FILE = "de.tbosch.tools.gui.error_writing_excel_file";

	/**
	 * @see AbstractBusinessException#AbstractBusinessException()
	 */
	public GuiBusinessException() {
		super();
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String, Throwable))
	 * @param message The message
	 * @param cause The cause Exception
	 * @param params The parameters passed
	 */
	public GuiBusinessException(String message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(String))
	 * @param message The message
	 * @param params The parameters passed
	 */
	public GuiBusinessException(String message, Object... params) {
		super(message, params);
	}

	/**
	 * @see AbstractBusinessException#AbstractBusinessException(Throwable))
	 * @param cause The cause Exception
	 */
	public GuiBusinessException(Throwable cause) {
		super(cause);
	}

}
