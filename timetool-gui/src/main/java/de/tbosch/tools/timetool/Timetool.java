package de.tbosch.tools.timetool;

import de.tbosch.tools.timetool.configuration.ApplicationConfiguration;
import de.tbosch.tools.timetool.utils.context.TimetoolContext;

/**
 * The main class for application startup.
 * 
 * @author Thomas Bosch
 */
public final class Timetool {

	/**
	 * Main class.
	 */
	private Timetool() {
		// Main class
	}

	/**
	 * The main method.
	 * 
	 * @param args The arguments of the call
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		TimetoolContext.init(ApplicationConfiguration.class);
		TimetoolThread timetoolThread = TimetoolContext.getBeansByType(TimetoolThread.class).get(0);
		timetoolThread.start();
		try {
			timetoolThread.join();
		} catch (InterruptedException e) {
			// Close will be done in finally block
			timetoolThread.interrupt();
		} finally {
			TimetoolContext.close();
		}
	}

}
