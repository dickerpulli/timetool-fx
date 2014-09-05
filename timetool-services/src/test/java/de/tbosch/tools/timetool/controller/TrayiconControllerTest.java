package de.tbosch.tools.timetool.controller;

import static org.junit.Assert.assertTrue;

import java.awt.SystemTray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringTest;

public class TrayiconControllerTest extends AbstractSpringTest {

	@Autowired
	private TrayiconController trayiconController;

	@Test
	public void testStartup() {
		int before = SystemTray.getSystemTray().getTrayIcons().length;
		trayiconController.registerTrayIcon();
		assertTrue(before < SystemTray.getSystemTray().getTrayIcons().length);
	}

}
