package de.tbosch.tools.timetool;

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
		TimetoolThread timetoolThread = (TimetoolThread) TimetoolContext.getBean("timetoolThread");
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
