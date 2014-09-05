package de.tbosch.tools.timetool.utils.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * A timer for updating the active timeslot.
 * 
 * @author Thomas Bosch
 */
public class TimeslotTimerTask implements Runnable {

	private static final Log LOG = LogFactory.getLog(TimeslotTimerTask.class);

	@Autowired
	private TimeslotService timeslotService;

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
				// End the timeslot
				timeslotService.deactivateTimeslot(activeTimeslot.getId());
			}
			LogUtils.logDebug("... updating done", LOG);
		} else {
			LogUtils.logDebug("no active timeslot found", LOG);
		}
	}

}
