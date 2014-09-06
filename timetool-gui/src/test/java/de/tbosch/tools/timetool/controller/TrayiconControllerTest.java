package de.tbosch.tools.timetool.controller;

import static org.junit.Assert.assertTrue;

import java.awt.SystemTray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;

@ContextConfiguration(classes = { TestConfiguration.class })
public class TrayiconControllerTest extends AbstractSpringJavaConfigTest {

	@Autowired
	private TrayiconController trayiconController;

	@Test
	public void testStartup() {
		int before = SystemTray.getSystemTray().getTrayIcons().length;
		trayiconController.registerTrayIcon();
		assertTrue(before < SystemTray.getSystemTray().getTrayIcons().length);
	}

}
