package de.tbosch.tools.timetool.utils.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeslotTimerTask.class);

	@Autowired
	private TimeslotService timeslotService;

	/**
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
		if (activeTimeslot != null) {
			LogUtils.logDebug("active timeslot found, updating endtime ...", LOGGER);
			Timeslot timeslot = timeslotService.updateEndtime(activeTimeslot);
			if (timeslot == null) {
				LogUtils.logWarn("Computer was down, timeslot will be stopped", LOGGER);
				// End the timeslot
				timeslotService.deactivateTimeslot(activeTimeslot.getId());
			}
			LogUtils.logDebug("... updating done", LOGGER);
		} else {
			LogUtils.logDebug("no active timeslot found", LOGGER);
		}
	}

}
