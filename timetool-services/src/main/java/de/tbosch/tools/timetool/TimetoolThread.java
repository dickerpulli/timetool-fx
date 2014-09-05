package de.tbosch.tools.timetool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.controller.TrayiconController;
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
	private SessionFactory sessionFactory;

	@Override
	public void run() {
		LogUtils.logInfo("running thread", LOG);
		SwingExceptionHandler.registerExceptionHandler();
		timetoolEvents.registerTrayIcon();
		configureDatabase();
		while (!isInterrupted()) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		LogUtils.logInfo("exiting thread", LOG);
	}

	/**
	 * Configure HSQL-DB specific properties.
	 */
	private void configureDatabase() {
		sessionFactory.openSession().createSQLQuery("SET WRITE_DELAY 1").executeUpdate();
		sessionFactory.openSession().createSQLQuery("SET FILES LOG SIZE 1").executeUpdate();
		sessionFactory.openSession().createSQLQuery("CHECKPOINT").executeUpdate();
	}

}
