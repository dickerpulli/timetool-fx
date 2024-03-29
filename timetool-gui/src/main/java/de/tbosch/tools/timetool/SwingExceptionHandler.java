package de.tbosch.tools.timetool;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tbosch.tools.timetool.exception.AbstractBusinessException;
import de.tbosch.tools.timetool.utils.LogUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Handler for all exception occuring in AWT threads.
 * 
 * @author Thomas Bosch
 */
public class SwingExceptionHandler implements UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwingExceptionHandler.class);

	/**
	 * Registers this Handler.
	 */
	public static void registerExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new SwingExceptionHandler());
	}

	/**
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread arg0, Throwable t) {
		LogUtils.logInfo("exception thrown - handle exception in AWTExceptioHandler", LOGGER);
		String text = t.getLocalizedMessage();
		if (!(t instanceof AbstractBusinessException)) {
			LogUtils.logDebug("Technical Exception handled: ", t, LOGGER);
			text = t.getClass().getCanonicalName() + " at " + t.getStackTrace()[0].getClassName() + ":" + t.getStackTrace()[0].getLineNumber() + "\n" + text;
		}
		JOptionPane.showMessageDialog(null, text, MessageHelper.getMessage("title.error"), JOptionPane.ERROR_MESSAGE);
	}

}
