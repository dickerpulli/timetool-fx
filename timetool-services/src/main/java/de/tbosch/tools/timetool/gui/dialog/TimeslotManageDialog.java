package de.tbosch.tools.timetool.gui.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.gui.model.Day;
import de.tbosch.tools.timetool.utils.SwingUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for showing all timeslots.
 * 
 * @author Thomas Bosch
 */
public class TimeslotManageDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private GuiController guiController;

	/**
	 * Constructor. Model type.
	 * 
	 * @param guiController The controller
	 */
	public TimeslotManageDialog(GuiController guiController) {
		this.guiController = guiController;
		setTitle(MessageHelper.getMessage("frame.manageTimeslot.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		init();
	}

	/**
	 * Adds the components to the frame.
	 */
	public void init() {
		final Dialog thisDialog = this;
		LayoutManager layout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
		setLayout(layout);

		// Timeslots list to edit
		JScrollPane scrollPane = new JScrollPane();
		final JTree timeslotsTree = new JTree();
		timeslotsTree.setRootVisible(true);
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		timeslotsTree.setSelectionModel(selectionModel);
		selectionModel.setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		scrollPane.setViewportView(timeslotsTree);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		add(scrollPane);

		// Set all marked timeslots and show actual month
		refreshTree(timeslotsTree);

		// Ok Button
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

		// Popupmenu for deleting timeslots
		final JPopupMenu singlePopup = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem(MessageHelper.getMessage("menu.item.delete"));
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.removeTimeslot(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		singlePopup.add(deleteItem);

		// ... mark timeslots
		JMenuItem markMenuItem = new JMenuItem(MessageHelper.getMessage("menu.item.mark"));
		markMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.toggleMarkingTimeslots(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		singlePopup.add(markMenuItem);

		// ... and editing timeslots
		JMenuItem editItem = new JMenuItem(MessageHelper.getMessage("menu.item.edit"));
		editItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.editTimeslot(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		singlePopup.add(editItem);

		// ... and sending timeslots
		JMenuItem sendItem = new JMenuItem(MessageHelper.getMessage("menu.item.send"));
		sendItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.transferTimeslot(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		singlePopup.add(sendItem);

		// Another menu for sumerization of timeslots
		final JPopupMenu multipleTimeslotPopup = new JPopupMenu();
		JMenuItem sumMenuItem = new JMenuItem(MessageHelper.getMessage("menu.item.sum"));
		sumMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.addTimeslots(thisDialog, timeslotsTree);
			}
		});
		multipleTimeslotPopup.add(sumMenuItem);

		// ... marking multiple timeslots
		JMenuItem markMultipleMenuItem = new JMenuItem(MessageHelper.getMessage("menu.item.mark"));
		markMultipleMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.toggleMarkingTimeslots(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		multipleTimeslotPopup.add(markMultipleMenuItem);

		// ... and sending timeslots
		JMenuItem sendMultipleTimeslotItem = new JMenuItem(MessageHelper.getMessage("menu.item.send"));
		sendMultipleTimeslotItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.transferTimeslot(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		multipleTimeslotPopup.add(sendMultipleTimeslotItem);

		// Another menu for sumerization of timeslots
		final JPopupMenu multipleDaysPopup = new JPopupMenu();
		JMenuItem timesheetMultipleDaysMenuItem = new JMenuItem(MessageHelper.getMessage("menu.item.timesheet"));
		timesheetMultipleDaysMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.openTimesheetDialog(thisDialog, timeslotsTree);
			}
		});
		multipleDaysPopup.add(timesheetMultipleDaysMenuItem);

		// A menu for one selected day
		final JPopupMenu singleDaysPopup = new JPopupMenu();
		JMenuItem sendSingleDayItem = new JMenuItem(MessageHelper.getMessage("menu.item.send"));
		sendSingleDayItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.transferDay(thisDialog, timeslotsTree);
				refreshTree(timeslotsTree);
			}
		});
		singleDaysPopup.add(sendSingleDayItem);

		// timesheet single day
		JMenuItem timesheetSingleDaysMenuItem = new JMenuItem(MessageHelper.getMessage("menu.item.timesheet"));
		timesheetSingleDaysMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.openTimesheetDialog(thisDialog, timeslotsTree);
			}
		});
		singleDaysPopup.add(timesheetSingleDaysMenuItem);

		// Add menu to every item in tree
		timeslotsTree.add(singlePopup);
		// 3 clicks to expand tree elements
		timeslotsTree.setToggleClickCount(3);
		timeslotsTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtils.isSelectedNodesLeaf(timeslotsTree)) {
					if (SwingUtils.isDoubleClick(e)) {
						if (timeslotsTree.getSelectionCount() == 1) {
							guiController.editTimeslot(thisDialog, timeslotsTree);
							refreshTree(timeslotsTree);
						}
					} else if (SwingUtilities.isRightMouseButton(e)) {
						if (timeslotsTree.getSelectionCount() == 1) {
							singlePopup.show(timeslotsTree, e.getX(), e.getY());
						}
						if (timeslotsTree.getSelectionCount() > 1) {
							multipleTimeslotPopup.show(timeslotsTree, e.getX(), e.getY());
						}
					}
				} else {
					if (SwingUtils.isDoubleClick(e)) {
						if (timeslotsTree.getSelectionCount() == 1) {
							TreeNode selectedNode = SwingUtils.getSelectedNodes(timeslotsTree).get(0);
							if (selectedNode != null) {
								guiController.transferDay(thisDialog, timeslotsTree);
								refreshTree(timeslotsTree);
							}
						}
					} else if (SwingUtilities.isRightMouseButton(e)) {
						if (timeslotsTree.getSelectionCount() > 1 && allSelectedNodesAreDays(timeslotsTree)) {
							multipleDaysPopup.show(timeslotsTree, e.getX(), e.getY());
						} else if (timeslotsTree.getSelectionCount() == 1 && allSelectedNodesAreDays(timeslotsTree)) {
							singleDaysPopup.show(timeslotsTree, e.getX(), e.getY());
						}
					}
				}
			}

			/**
			 * Checks if all selected nodes in the tree have an user-object of type Day.
			 * 
			 * @param timeslotsTree The tree
			 * @return <code>true</code> if all elements are Day's or nothing is selected
			 */
			private boolean allSelectedNodesAreDays(JTree timeslotsTree) {
				List<TreeNode> selectedNodes = SwingUtils.getSelectedNodes(timeslotsTree);
				for (TreeNode selectedNode : selectedNodes) {
					if (selectedNode instanceof DefaultMutableTreeNode) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedNode;
						Object userObject = node.getUserObject();
						if (!(userObject instanceof Day)) {
							return false;
						}
					}
				}
				return true;
			}
		});

		// Do the layout
		pack();
	}

	/**
	 * Refreshes the given JTree with the new data and expands the actual month.
	 * 
	 * @param timeslotsTree The tree
	 */
	private void refreshTree(final JTree timeslotsTree) {
		TreeNode rootTreeNode = guiController.createTreeFromTimeslotsPerProject();
		timeslotsTree.setModel(new DefaultTreeModel(rootTreeNode));
		guiController.expandActiveProjectAndMonth(rootTreeNode, timeslotsTree);
		guiController.highlightMarkedTimeslots(timeslotsTree);
	}

}
