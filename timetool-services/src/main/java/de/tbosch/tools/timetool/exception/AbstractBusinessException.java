package de.tbosch.tools.timetool.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Exception to signal business problems.
 * 
 * @author Thomas Bosch
 */
public abstract class AbstractBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String RESOURCE_BUNDLE = "exceptions";

	private Object[] params;

	/**
	 * @see RuntimeException#RuntimeException())
	 */
	public AbstractBusinessException() {
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable))
	 * @param message The message
	 * @param cause The cause Exception
	 * @param params The parameters passed
	 */
	public AbstractBusinessException(String message, Throwable cause, Object... params) {
		super(message, cause);
		this.params = params;
	}

	/**
	 * @see RuntimeException#RuntimeException(String))
	 * @param message The message
	 * @param params The parameters passed
	 */
	public AbstractBusinessException(String message, Object... params) {
		super(message);
		this.params = params;
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 * @param cause The cause Exception
	 */
	public AbstractBusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		String message = super.getMessage();
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
		if (bundle != null) {
			// try to get translated message from resource bundle and fill in the message parameters
			message = bundle.getString(message);
			message = MessageFormat.format(message, params);
		}
		return message;
	}
}
