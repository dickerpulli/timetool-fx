package de.tbosch.tools.timetool.utils;

import org.apache.commons.logging.Log;

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
		FATAL, ERROR, WARN, INFO, DEBUG, TRACE
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
	 * <li>{@link Log#isFatalEnabled()}</li>
	 * <li>{@link Log#isInfoEnabled()}</li>
	 * <li>{@link Log#isTraceEnabled()}</li>
	 * <li>{@link Log#isWarnEnabled()}</li>
	 * </ul>
	 * 
	 * @param message The message to log
	 * @param logLevel The level to log : {@link Level}
	 * @param log The Log itself
	 */
	public static void log(Object message, Level logLevel, Log log) {
		log(message, null, logLevel, log);
	}

	/**
	 * Logs a message with using check if logging in this level is enabled.
	 * <ul>
	 * <li>{@link Log#isDebugEnabled()}</li>
	 * <li>{@link Log#isErrorEnabled()}</li>
	 * <li>{@link Log#isFatalEnabled()}</li>
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
	public static void log(Object message, Throwable t, Level logLevel, Log log) {
		switch (logLevel) {
		case FATAL:
			logFatal(message, t, log);
			break;
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
	 * Logs a message with level FATAL and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isFatalEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logFatal(Object message, Log log) {
		logFatal(message, null, log);
	}

	/**
	 * Logs a message with level DEBUG and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isDebugEnabled()}
	 * 
	 * @param message The message to log
	 * @param log The Log itself
	 */
	public static void logDebug(Object message, Log log) {
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
	public static void logError(Object message, Log log) {
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
	public static void logInfo(Object message, Log log) {
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
	public static void logTrace(Object message, Log log) {
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
	public static void logWarn(Object message, Log log) {
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
	public static void logDebug(Object message, Throwable t, Log log) {
		if (log.isDebugEnabled()) {
			log.debug(message, t);
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
	public static void logError(Object message, Throwable t, Log log) {
		if (log.isErrorEnabled()) {
			log.error(message, t);
		}
	}

	/**
	 * Logs a message with level FATAL and uses check if logging is enabled.<br>
	 * 
	 * {@link Log#isFatalEnabled()}
	 * 
	 * @param message The message to log
	 * @param t A Throwable to attach
	 * @param log The Log itself
	 */
	public static void logFatal(Object message, Throwable t, Log log) {
		if (log.isFatalEnabled()) {
			log.fatal(message, t);
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
	public static void logInfo(Object message, Throwable t, Log log) {
		if (log.isInfoEnabled()) {
			log.info(message, t);
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
	public static void logTrace(Object message, Throwable t, Log log) {
		if (log.isTraceEnabled()) {
			log.trace(message, t);
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
	public static void logWarn(Object message, Throwable t, Log log) {
		if (log.isWarnEnabled()) {
			log.warn(message, t);
		}
	}

}
