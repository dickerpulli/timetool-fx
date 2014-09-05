package de.tbosch.tools.timetool.utils.timer;

import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.controller.TrayiconController;

/**
 * A timer for updating the icon.
 * 
 * @author Thomas Bosch
 */
public class IconTimerTask implements Runnable {

	@Autowired
	private TrayiconController trayiconController;

	/**
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		// Check icon
		trayiconController.initIcon();
	}

}
