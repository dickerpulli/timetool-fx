package de.tbosch.tools.timetool.controller;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import de.tbosch.tools.timetool.TimetoolThread;
import de.tbosch.tools.timetool.exception.GuiBusinessException;
import de.tbosch.tools.timetool.gui.JXTrayIcon;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.ProjectService;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.DateUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Controller for management of actions and events on the tray icon.
 * 
 * @author Thomas Bosch
 */
@Controller
public class TrayiconController {

	@Autowired
	private JXTrayIcon trayIcon;

	@Autowired
	@Qualifier("imageAktiv")
	private Image imageAktiv;

	@Autowired
	@Qualifier("imageInaktiv")
	private Image imageInaktiv;

	@Autowired
	private TimetoolThread timetoolThread;

	@Autowired
	private TimeslotService timeslotService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private GuiController guiController;

	/**
	 * Is called before destroying this bean by Spring's lifecycle management.
	 */
	@PreDestroy
	public void preDestroy() {
		trayIcon.dispose();
		SystemTray.getSystemTray().remove(trayIcon);
	}

	/**
	 * Register menu and all actions on the trayicon.
	 */
	public void registerTrayIcon() {
		// Autosize the icon image
		trayIcon.setImageAutoSize(true);

		// Add tray icon to the system tray
		SystemTray systemTray = SystemTray.getSystemTray();
		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			throw new GuiBusinessException(GuiBusinessException.COMMON_AWT_ERROR, e);
		}

		// Always set the active timeslots to inactive.
		timeslotService.deactivateActiveTimeslot();

		// Adds the popup menu to trayicon
		registerPopupMenu();

		// register events on trayicon itself
		registerEvents();

		// sets the image of the icon
		initIcon();
	}

	/**
	 * Initializes the image of the tray icon.
	 */
	public void initIcon() {
		Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
		if (activeTimeslot == null) {
			trayIcon.setImage(imageInaktiv);
		} else {
			trayIcon.setImage(imageAktiv);
		}
		setTrayiconTooltip();
	}

	/**
	 * Registers all events on the trayicon.
	 */
	private void registerEvents() {
		trayIcon.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					rightMouseButtonClicked();
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					leftMouseButtonClicked();
				}
			}

		});
		trayIcon.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				setTrayiconTooltip();
			}
		});
	}

	/**
	 * Registers a popup menu on trayicon.
	 */
	private void registerPopupMenu() {
		// Create a new popup menu for options and controls
		JPopupMenu popup = new JPopupMenu(MessageHelper.getMessage("menu.label"));

		// Sub menu for choosing active project
		JMenu menu = new JMenu(MessageHelper.getMessage("menu.projects.label"));

		// Control item to add a new project
		JMenuItem itemAdd = new JMenuItem(MessageHelper.getMessage("menu.projects.manageProjects"));
		itemAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.manageProjects();
			}

		});
		menu.add(itemAdd);

		// Separator
		menu.addSeparator();

		// Fill in all known project to choose from
		List<Project> projects = projectService.getAllProjects();
		for (final Project project : projects) {
			JMenuItem item = new JMenuItem(projectService.getFullName(project));
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					projectService.setActiveProject(project.getId());
					refreshProjects();
				}

			});
			if (project.isActive()) {
				item.setBackground(Color.DARK_GRAY);
				item.setForeground(Color.WHITE);
			}
			menu.add(item);
		}
		popup.add(menu);

		// Separator
		popup.addSeparator();

		// Exit Item to shutdown the program
		JMenuItem itemView = new JMenuItem(MessageHelper.getMessage("menu.item.view"));
		itemView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.manageTimeslots();
			}

		});
		popup.add(itemView);

		// Separator
		popup.addSeparator();

		// Exit Item to shutdown the program
		JMenuItem itemAbout = new JMenuItem(MessageHelper.getMessage("menu.item.about"));
		itemAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, MessageHelper.getMessage("message.about", MessageHelper.getMessage("rev")),
						MessageHelper.getMessage("message.about.title"), JOptionPane.INFORMATION_MESSAGE);
			}

		});
		popup.add(itemAbout);

		// Separator
		popup.addSeparator();

		// Exit Item to shutdown the program
		JMenuItem itemExit = new JMenuItem(MessageHelper.getMessage("menu.item.exit"));
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timetoolThread.interrupt();
			}

		});
		popup.add(itemExit);

		trayIcon.setJPopupMenu(popup);
	}

	/**
	 * If the right mouse button is clicked.
	 */
	private void rightMouseButtonClicked() {

	}

	/**
	 * If the left mouse button is clicked.
	 */
	private void leftMouseButtonClicked() {
		// Start or stop active timelot
		timeslotService.toggleTimeslotActivation();

		// Reset the icon image
		initIcon();
	}

	/**
	 * Refresh the list of projects in the popup menu.
	 */
	public void refreshProjects() {
		// Update the Popup Menu
		registerPopupMenu();

		// Update tooltip
		initIcon();
	}

	/**
	 * Sets the project name and timeslot data as tooltip on tray icon.
	 */
	private void setTrayiconTooltip() {
		Project activeProject = projectService.getActiveProject();
		if (activeProject != null) {
			String fullName = projectService.getFullName(activeProject);
			Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
			if (activeTimeslot != null) {
				fullName += " : " + DateUtils.getDifferenceAsString(activeTimeslot.getStarttime(), activeTimeslot.getEndtime(), true);
			}
			trayIcon.setToolTip(fullName);
		}
	}
}
