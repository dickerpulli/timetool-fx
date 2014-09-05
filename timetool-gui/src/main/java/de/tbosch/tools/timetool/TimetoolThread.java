package de.tbosch.tools.timetool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.controller.TrayiconController;
import de.tbosch.tools.timetool.utils.DatabaseInitializer;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * The main thread for the tool that is running to hold the tray icon.
 * 
 * @author Thomas Bosch
 */
public class TimetoolThread extends Thread {

	private static final Log LOG = LogFactory.getLog(TimetoolThread.class);

	@Autowired
	private TrayiconController timetoolEvents;

	@Autowired
	private DatabaseInitializer databaseInitializer;

	@Override
	public void run() {
		LogUtils.logInfo("running thread", LOG);
		SwingExceptionHandler.registerExceptionHandler();
		timetoolEvents.registerTrayIcon();
		databaseInitializer.configureDatabase();
		while (!isInterrupted()) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		LogUtils.logInfo("exiting thread", LOG);
	}

}
