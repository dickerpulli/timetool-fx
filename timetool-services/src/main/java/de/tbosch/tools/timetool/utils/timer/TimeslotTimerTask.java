package de.tbosch.tools.timetool.utils.timer;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.controller.TrayiconController;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.LogUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * A timer for updating the active timeslot.
 * 
 * @author Thomas Bosch
 */
public class TimeslotTimerTask implements Runnable {

	private static final Log LOG = LogFactory.getLog(TimeslotTimerTask.class);

	@Autowired
	private TimeslotService timeslotService;

	@Autowired
	private TrayiconController trayiconController;

	/**
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
		if (activeTimeslot != null) {
			LogUtils.logDebug("active timeslot found, updating endtime ...", LOG);
			Timeslot timeslot = timeslotService.updateEndtime(activeTimeslot);
			if (timeslot == null) {
				LogUtils.logWarn("Computer was down, timeslot will be stopped", LOG);
				JOptionPane.showMessageDialog(null, MessageHelper.getMessage("error.too_much_time"), MessageHelper.getMessage("title.warn"),
						JOptionPane.WARNING_MESSAGE);
				// End the timeslot
				timeslotService.deactivateTimeslot(activeTimeslot.getId());
				// Mark tray icon as stopped
				trayiconController.initIcon();
			}
			LogUtils.logDebug("... updating done", LOG);
		} else {
			LogUtils.logDebug("no active timeslot found", LOG);
		}
	}
}
