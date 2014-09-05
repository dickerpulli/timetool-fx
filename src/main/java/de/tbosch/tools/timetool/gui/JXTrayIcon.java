package de.tbosch.tools.timetool.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Tray icon extention for the system tray. With this tray icon you can set up an application running in system tray with some menu operations to perforn on the
 * icon.
 * 
 * @author Thomas Bosch
 */
public class JXTrayIcon extends TrayIcon {

	private JPopupMenu menu;

	private static JDialog dialog;
	static {
		dialog = new JDialog((Frame) null);
		dialog.setUndecorated(true);
		dialog.setAlwaysOnTop(true);
	}

	private static PopupMenuListener popupListener = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			dialog.setVisible(false);
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			dialog.setVisible(false);
		}
	};

	/**
	 * Constructor with image.
	 * 
	 * @param image The image
	 */
	public JXTrayIcon(Image image) {
		super(image);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				showJPopupMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showJPopupMenu(e);
			}
		});
	}

	/**
	 * Show the popup menu on mouse event.
	 * 
	 * @param e The mouse event
	 */
	protected void showJPopupMenu(MouseEvent e) {
		if (e.isPopupTrigger() && menu != null) {
			Dimension size = menu.getPreferredSize();
			showJPopupMenu(e.getX(), e.getY() - size.height);
		}
	}

	/**
	 * Show the popup menu with coordinates.
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	protected void showJPopupMenu(int x, int y) {
		dialog.setLocation(x, y);
		dialog.setVisible(true);
		menu.show(dialog.getContentPane(), 0, 0);
		// popup works only for focused windows
		dialog.toFront();
	}

	/**
	 * @return The popup menu
	 */
	public JPopupMenu getJPopupMenu() {
		return menu;
	}

	/**
	 * @param theMenu The popup menu to set
	 */
	public void setJPopupMenu(JPopupMenu theMenu) {
		if (this.menu != null) {
			this.menu.removePopupMenuListener(popupListener);
		}
		this.menu = theMenu;
		menu.addPopupMenuListener(popupListener);
	}

	/**
	 * Close the tray icon.
	 */
	public void dispose() {
		dialog.dispose();
	}

}
