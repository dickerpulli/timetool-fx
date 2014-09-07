package de.tbosch.tools.timetool.utils;

import org.slf4j.Logger;

/**
 * Utility class for logging.
 * 
 * @author Thomas Bosch
 */
public final class LogUtils {

	/**
	 * The level to log.
	 * 
	 * @author thomas.bosch
	 */
	public enum Level {
		ERROR, WARN, INFO, DEBUG, TRACE
	}

	/**
	 * Utils.
	 */
	private LogUtils() {
		// Utils
	}

	/**
	 * Logs a message with using check if logging in this level is enabled.
	 * <ul>
	 * <li>{@link Log#isDebugEnabled()}</li>
	 * <li>{@link Log#isErrorEnabled()}</li>
	 * <li>{@link Log#isInfoEnabled()}</li>
	 * <li>{@link Log#isTraceEnabled()}</li>
	 * <li>{@link Log#isWarnEnabled()}</li>
	 * </ul>
	 * 
	 * @param message The message to log
	 * @param logLevel The level to log : {@link Level}
	 * @param log The Log itself
	 */
	public static void log(Object message, Level logLevel, Logger log) {
		log(message, null, logLevel, log);
	}

	/**
	 * Logs a message with using check if logging in this level is enabled.
	 * <ul>
	 * <li>{@link Log#isDebugEnabled()}</li>
	 * <li>{@link Log#isErrorEnabled()}</li>
	 * <li>{@link Log#isInfoEnabled()}</li>
	 * <li>{@link Log#isTraceEnabled()}</li>
	 * <li>{@link Log#isWarnEnabled()}</li>
	 * </ul>
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param logLevel The level to log : {@link Level}
	 * @param log The Log itself
	 */
	public static void log(Object message, Throwable t, Level logLevel, Logger log) {
		switch (logLevel) {
		case DEBUG:
			logDebug(message, t, log);
			break;
		case ERROR:
			logError(message, t, log);
			break;
		case INFO:
			logInfo(message, t, log);
			break;
		case TRACE:
			logTrace(message, t, log);
			break;
		case WARN:
			logWarn(message, t, log);
			break;
		default:
			throw new IllegalArgumentException("log level " + logLevel + " not handled");
		}
	}

	/**
	 * Logs a message with level DEBUG and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isDebugEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logDebug(Object message, Logger log) {
		logDebug(message, null, log);
	}

	/**
	 * Logs a message with level ERROR and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isErrorEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logError(Object message, Logger log) {
		logError(message, null, log);
	}

	/**
	 * Logs a message with level INFO and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isInfoEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logInfo(Object message, Logger log) {
		logInfo(message, null, log);
	}

	/**
	 * Logs a message with level TRACE and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isTraceEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logTrace(Object message, Logger log) {
		logTrace(message, null, log);
	}

	/**
	 * Logs a message with level WARN and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isWarnEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logWarn(Object message, Logger log) {
		logWarn(message, null, log);
	}

	/**
	 * Logs a message with level DEBUG and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isDebugEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logDebug(Object message, Throwable t, Logger log) {
		if (log.isDebugEnabled()) {
			log.debug(message.toString(), t);
		}
	}

	/**
	 * Logs a message with level ERROR and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isErrorEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logError(Object message, Throwable t, Logger log) {
		if (log.isErrorEnabled()) {
			log.error(message.toString(), t);
		}
	}

	/**
	 * Logs a message with level INFO and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isInfoEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logInfo(Object message, Throwable t, Logger log) {
		if (log.isInfoEnabled()) {
			log.info(message.toString(), t);
		}
	}

	/**
	 * Logs a message with level TRACE and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isTraceEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logTrace(Object message, Throwable t, Logger log) {
		if (log.isTraceEnabled()) {
			log.trace(message.toString(), t);
		}
	}

	/**
	 * Logs a message with level WARN and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isWarnEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logWarn(Object message, Throwable t, Logger log) {
		if (log.isWarnEnabled()) {
			log.warn(message.toString(), t);
		}
	}

}
