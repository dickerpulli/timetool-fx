package de.tbosch.tools.timetool.gui.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.utils.SwingUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for adding new customers.
 * 
 * @author Thomas Bosch
 */
public class ProjectManageDialog extends JDialog {

	private static final long serialVersionUID = 14633808809997205L;

	private final GuiController guiController;

	/**
	 * Constructor.
	 * 
	 * @param guiController The controller
	 */
	public ProjectManageDialog(GuiController guiController) {
		this.guiController = guiController;
		setTitle(MessageHelper.getMessage("frame.manageProjects.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		init();
	}

	/**
	 * Adds the components to the frame.
	 */
	private void init() {
		LayoutManager layout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
		setLayout(layout);

		// Customers list to edit
		JScrollPane scrollPane = new JScrollPane();
		final JList<Project> projectsList = new JList<Project>(guiController.getAllProjects().toArray(new Project[] {}));
		projectsList.setMinimumSize(new Dimension(20, 20));
		projectsList.setPreferredSize(new Dimension(200, 200));
		projectsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Popupmenu for actions on projects
		final JPopupMenu popup = new JPopupMenu();
		final Dialog thisDialog = this;
		JMenuItem addItem = new JMenuItem(MessageHelper.getMessage("menu.item.add"));
		addItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.addProject(thisDialog);
				projectsList.setListData(guiController.getAllProjects().toArray(new Project[] {}));
				guiController.refreshProjects();
			}
		});
		popup.add(addItem);
		JMenuItem editItem = new JMenuItem(MessageHelper.getMessage("menu.item.edit"));
		editItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.editProject(thisDialog, projectsList.getSelectedValue());
				projectsList.setListData(guiController.getAllProjects().toArray(new Project[] {}));
				guiController.refreshProjects();
			}
		});
		popup.add(editItem);
		JMenuItem deleteItem = new JMenuItem(MessageHelper.getMessage("menu.item.delete"));
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.removeProject(thisDialog, projectsList.getSelectedValue());
				projectsList.setListData(guiController.getAllProjects().toArray(new Project[] {}));
				guiController.refreshProjects();
			}
		});
		popup.add(deleteItem);
		projectsList.add(popup);
		projectsList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtils.isDoubleClick(e)) {
					guiController.editProject(thisDialog, projectsList.getSelectedValue());
					projectsList.setListData(guiController.getAllProjects().toArray(new Project[] {}));
					guiController.refreshProjects();
				} else if (SwingUtilities.isRightMouseButton(e)) {
					popup.show(projectsList, e.getX(), e.getY());
				}
			}
		});
		scrollPane.setViewportView(projectsList);
		add(scrollPane);

		// Close Button
		FlowLayout buttonLayout = new FlowLayout(FlowLayout.RIGHT);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.setMaximumSize(new Dimension((int) buttonPanel.getMaximumSize().getWidth(), 50));
		JButton okButton = new JButton(MessageHelper.getMessage("button.close"));
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(okButton);
		add(buttonPanel);

		// Do the layout
		pack();
	}
}
