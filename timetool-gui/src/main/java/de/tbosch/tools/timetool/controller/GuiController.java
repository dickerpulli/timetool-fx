package de.tbosch.tools.timetool.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import de.tbosch.tools.timetool.exception.GuiBusinessException;
import de.tbosch.tools.timetool.gui.dialog.CustomerAddDialog;
import de.tbosch.tools.timetool.gui.dialog.CustomerEditDialog;
import de.tbosch.tools.timetool.gui.dialog.CustomerManageDialog;
import de.tbosch.tools.timetool.gui.dialog.ProjectAddDialog;
import de.tbosch.tools.timetool.gui.dialog.ProjectEditDialog;
import de.tbosch.tools.timetool.gui.dialog.ProjectManageDialog;
import de.tbosch.tools.timetool.gui.dialog.TimeslotEditDialog;
import de.tbosch.tools.timetool.gui.dialog.TimeslotManageDialog;
import de.tbosch.tools.timetool.gui.dialog.TimeslotSendDialog;
import de.tbosch.tools.timetool.gui.dialog.TimeslotTimesheetDialog;
import de.tbosch.tools.timetool.gui.model.Day;
import de.tbosch.tools.timetool.gui.model.Month;
import de.tbosch.tools.timetool.gui.model.Ticket;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.model.JiraSettings;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.ConfigurationService;
import de.tbosch.tools.timetool.service.CustomerService;
import de.tbosch.tools.timetool.service.JiraService;
import de.tbosch.tools.timetool.service.ProjectService;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.SwingUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;
import de.tbosch.tools.timetool.utils.excel.TimeslotExcelWorker;

/**
 * Controller for controlling Gui elements.
 * 
 * @author Thomas Bosch
 */
@Controller
public class GuiController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TimeslotService timeslotService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private TrayiconController trayiconController;

	@Autowired
	private JiraService jiraService;

	/**
	 * Manage the projects.
	 */
	public void manageProjects() {
		ProjectManageDialog dialog = new ProjectManageDialog(this);

		// Centering dialog on desktop screen
		SwingUtils.centerOnScreen(dialog);

		// Show dialog
		dialog.setVisible(true);
	}

	/**
	 * Adds a new project to the list.
	 * 
	 * @param parent The parent dialog
	 */
	public void addProject(Dialog parent) {
		ProjectAddDialog dialog = new ProjectAddDialog(this, parent);

		// Centering dialog on desktop screen
		SwingUtils.centerOnScreen(dialog);

		// Show dialog
		dialog.setVisible(true);
	}

	/**
	 * Manage the customers.
	 * 
	 * @param parent The parent dialog
	 */
	public void manageCustomers(Dialog parent) {
		CustomerManageDialog dialog = new CustomerManageDialog(this, parent);

		// Centering dialog on desktop screen
		SwingUtils.centerOnScreen(dialog);

		// Show dialog
		dialog.setVisible(true);
	}

	/**
	 * Adds a new customer.
	 * 
	 * @param parent The parent dialog
	 */
	public void addCustomer(Dialog parent) {
		CustomerAddDialog dialog = new CustomerAddDialog(this, parent);

		// Centering dialog on desktop screen
		SwingUtils.centerOnScreen(dialog);

		// Show dialog
		dialog.setVisible(true);
	}

	/**
	 * Creates a new customer with the given name.
	 * 
	 * @param name The name
	 */
	public void createCustomer(String name) {
		customerService.createCustomer(name);
	}

	/**
	 * Removes a customer.
	 * 
	 * @param parent The parent dialog
	 * @param customer The customer
	 */
	public void removeCustomer(Dialog parent, Customer customer) {
		int answer = JOptionPane.showConfirmDialog(parent, MessageHelper.getMessage("confirm.realyWantToRemove"), MessageHelper.getMessage("title.confirm"),
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			if (customer != null) {
				customerService.deleteCustomer(customer);
			}
		}
	}

	/**
	 * Gets a list of all customers.
	 * 
	 * @return The list
	 */
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	/**
	 * Creates a new project.
	 * 
	 * @param name The name of the project
	 * @param customerId The id of the associated customer
	 */
	public void createProject(String name, long customerId) {
		projectService.createProject(name, customerId);
	}

	/**
	 * Removes a project.
	 * 
	 * @param parent The parent dialog
	 * @param project The project
	 */
	public void removeProject(Dialog parent, Project project) {
		int answer = JOptionPane.showConfirmDialog(parent, MessageHelper.getMessage("confirm.realyWantToRemove"), MessageHelper.getMessage("title.confirm"),
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			if (project != null) {
				projectService.deleteProject(project);
			}
		}
	}

	/**
	 * Gets all projects.
	 * 
	 * @return The list
	 */
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	/**
	 * Refresh the list of projects in the tray icon popup menu.
	 */
	public void refreshProjects() {
		trayiconController.refreshProjects();
	}

	/**
	 * Saves the project with the new data.
	 * 
	 * @param project The project
	 * @param name The name
	 * @param customerId The Id of the customer
	 */
	public void saveProject(Project project, String name, long customerId) {
		projectService.saveProject(project, name, customerId);
	}

	/**
	 * Edit the selected project.
	 * 
	 * @param parent The parent dialog
	 * @param project The Project
	 */
	public void editProject(Dialog parent, Project project) {
		if (project != null) {
			ProjectEditDialog dialog = new ProjectEditDialog(this, project, parent);

			// Centering dialog on desktop screen
			SwingUtils.centerOnScreen(dialog);

			// Show dialog
			dialog.setVisible(true);
		}
	}

	/**
	 * Saves the customer with new data.
	 * 
	 * @param id The customer id
	 * @param text The name
	 */
	public void saveCustomer(long id, String text) {
		customerService.saveCustomer(id, text);
	}

	/**
	 * Edit customer.
	 * 
	 * @param parent The parent dialog
	 * @param customer The customer to edit
	 */
	public void editCustomer(Dialog parent, Customer customer) {
		if (customer != null) {
			CustomerEditDialog dialog = new CustomerEditDialog(this, customer, parent);

			// Centering dialog on desktop screen
			SwingUtils.centerOnScreen(dialog);

			// Show dialog
			dialog.setVisible(true);
		}
	}

	/**
	 * Get all timeslots.
	 * 
	 * @return All timeslots
	 */
	public List<Timeslot> getAllTimeslots() {
		return timeslotService.getAllTimeslots();
	}

	/**
	 * Show all timeslots in view.
	 */
	public void manageTimeslots() {
		TimeslotManageDialog dialog = new TimeslotManageDialog(this);

		// Centering dialog on desktop screen
		SwingUtils.centerOnScreen(dialog);

		// Show dialog
		dialog.setVisible(true);
	}

	/**
	 * Returns all timeslots sorted in a swing tree.
	 * 
	 * @return The tree
	 */
	public TreeNode createTreeFromTimeslotsPerProject() {
		Map<String, List<Timeslot>> timeslotsPerProject = timeslotService.getAllTimeslotsPerProject();
		DefaultMutableTreeNode treeNodeRoot = new DefaultMutableTreeNode(MessageHelper.getMessage("tree.timeslots.root"));
		for (String projectName : timeslotsPerProject.keySet()) {
			DefaultMutableTreeNode treeNodeProject = new DefaultMutableTreeNode(projectName);
			List<Timeslot> allTimeslots = timeslotsPerProject.get(projectName);
			Map<LocalDate, Map<LocalDate, List<Timeslot>>> perMonthAndDay = TimeslotUtils.getTimeslotsPerMonthAndDay(allTimeslots);
			for (Entry<LocalDate, Map<LocalDate, List<Timeslot>>> inMonthEntry : perMonthAndDay.entrySet()) {
				LocalDate firstDayOfMonth = inMonthEntry.getKey();
				Month month = new Month(firstDayOfMonth);
				DefaultMutableTreeNode treeNodeMonth = new DefaultMutableTreeNode(month);
				for (Entry<LocalDate, List<Timeslot>> inDayEntry : inMonthEntry.getValue().entrySet()) {
					List<Timeslot> timeslots = inDayEntry.getValue();
					Day day = new Day(timeslots);
					month.addDay(day);
					DefaultMutableTreeNode treeNodeDay = new DefaultMutableTreeNode(day);
					for (Timeslot timeslot : timeslots) {
						DefaultMutableTreeNode treeNodeTimeslot = new DefaultMutableTreeNode(timeslot);
						treeNodeDay.add(treeNodeTimeslot);
					}
					treeNodeMonth.add(treeNodeDay);
				}
				treeNodeProject.add(treeNodeMonth);
			}
			treeNodeRoot.add(treeNodeProject);
		}
		return treeNodeRoot;
	}

	/**
	 * Expands the tree with the active project. Also expands the actual month.
	 * 
	 * @param timeslotsTree The tree to expand from
	 * @param rootTreeNode The root tree node
	 */
	public void expandActiveProjectAndMonth(TreeNode rootTreeNode, JTree timeslotsTree) {
		Project activeProject = projectService.getActiveProject();
		if (activeProject != null) {
			String activeProjectName = projectService.getFullName(activeProject);
			List<TreeNode> nodeProjects = SwingUtils.getAllChildren(rootTreeNode);
			for (TreeNode nodeProject : nodeProjects) {
				if (nodeProject instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode treeNodeProject = (DefaultMutableTreeNode) nodeProject;
					if (treeNodeProject.getUserObject() instanceof String) {
						String projectName = (String) treeNodeProject.getUserObject();
						if (projectName.equals(activeProjectName)) {
							TreePath path = new TreePath(rootTreeNode);
							path = path.pathByAddingChild(treeNodeProject);
							List<TreeNode> nodeMonths = SwingUtils.getAllChildren(treeNodeProject);
							for (TreeNode nodeMonth : nodeMonths) {
								if (nodeMonth instanceof DefaultMutableTreeNode) {
									DefaultMutableTreeNode treeNodeMonth = (DefaultMutableTreeNode) nodeMonth;
									if (treeNodeMonth.getUserObject() instanceof Month) {
										Month month = (Month) treeNodeMonth.getUserObject();
										if (month.getFirstOfMonth().equals(new LocalDate().withDayOfMonth(1))) {
											path = path.pathByAddingChild(treeNodeMonth);
											timeslotsTree.expandPath(path);
										}
									}
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Removes the timeslot that is selected.
	 * 
	 * @param parent The parent dialog
	 * @param timeslotsTree The path in the JTree
	 */
	public void removeTimeslot(Dialog parent, JTree timeslotsTree) {
		int answer = JOptionPane.showConfirmDialog(parent, MessageHelper.getMessage("confirm.realyWantToRemove"), MessageHelper.getMessage("title.confirm"),
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			Timeslot selectedTimeslot = SwingUtils.getSelectedLeafUserObject(timeslotsTree, Timeslot.class);
			if (selectedTimeslot != null) {
				timeslotService.deleteTimeslot(selectedTimeslot);
			}
		}
	}

	/**
	 * Edits the selected timeslot in a new dialog.
	 * 
	 * @param parentDialog The parent dialog
	 * @param timeslotsTree The JTree with the timeslots (one is selected)
	 */
	public void editTimeslot(Dialog parentDialog, JTree timeslotsTree) {
		Timeslot selectedTimeslot = SwingUtils.getSelectedLeafUserObject(timeslotsTree, Timeslot.class);
		if (selectedTimeslot != null) {
			if (selectedTimeslot.isMarked()) {
				throw new GuiBusinessException(GuiBusinessException.TIMESLOT_ALREADY_SENT);
			}

			TimeslotEditDialog dialog = new TimeslotEditDialog(this, selectedTimeslot, parentDialog);

			// Centering dialog on desktop screen
			SwingUtils.centerOnScreen(dialog);

			// Show dialog
			dialog.setVisible(true);
		}
	}

	/**
	 * Saves the new values to the given timeslot.
	 * 
	 * @param timeslot The timeslot
	 * @param starttime The starttime
	 * @param endtime The endtime
	 */
	public void saveTimeslot(Timeslot timeslot, Date starttime, Date endtime) {
		timeslotService.saveTimeslot(timeslot, starttime, endtime);
	}

	/**
	 * Sums up all selected timeslots in the tree and shows the sum in a new dialog.
	 * 
	 * @param parent The parent dialog
	 * @param timeslotsTree The tree
	 */
	public void addTimeslots(Dialog parent, JTree timeslotsTree) {
		List<Timeslot> timeslots = SwingUtils.getSelectedLeafsUserObjects(timeslotsTree, Timeslot.class);
		String sum = timeslotService.addTimeslots(timeslots);
		JOptionPane.showMessageDialog(parent, MessageHelper.getMessage("message.sum", sum));
	}

	/**
	 * Marks all selected timeslots in the tree, i.e. color them.
	 * 
	 * @param parent The parent dialog
	 * @param timeslotsTree The tree
	 */
	public void toggleMarkingTimeslots(Dialog parent, JTree timeslotsTree) {
		int answer = JOptionPane.showConfirmDialog(parent, MessageHelper.getMessage("confirm.realyWantToMark"), MessageHelper.getMessage("title.confirm"),
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			List<Timeslot> timeslots = SwingUtils.getSelectedLeafsUserObjects(timeslotsTree, Timeslot.class);
			timeslotService.toggleMarking(timeslots);
		}
	}

	/**
	 * Highlight all marked timeslots.
	 * 
	 * @param timeslotsTree The tree
	 */
	@SuppressWarnings("serial")
	public void highlightMarkedTimeslots(JTree timeslotsTree) {
		timeslotsTree.setCellRenderer(new DefaultTreeCellRenderer() {

			/**
			 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int,
			 *      boolean)
			 */
			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Color green = new Color(85, 190, 85);
				Color black = new Color(0, 0, 0);
				boolean markIt = false;
				if (node.getUserObject() instanceof Timeslot) {
					// First, mark all leafs in green
					Timeslot timeslot = (Timeslot) node.getUserObject();
					if (timeslot.isMarked()) {
						markIt = true;
					}
				} else if (node.getUserObject() instanceof Day) {
					// ... or day if all timeslots are marked
					Day day = (Day) node.getUserObject();
					if (day.isAllMarked()) {
						markIt = true;
					}
				} else if (node.getUserObject() instanceof Month) {
					// ... or mark complete month
					Month month = (Month) node.getUserObject();
					if (month.isAllMarked()) {
						markIt = true;
					}
				}
				if (markIt) {
					this.setForeground(green);
				} else {
					this.setForeground(black);
				}
				return this;
			}
		});
	}

	/**
	 * Gets all selected timeslots and create a new dialog to send those timeslots.
	 * 
	 * @param parent The parent dialog
	 * @param timeslotsTree The tree with selected times
	 */
	public void transferTimeslot(Dialog parent, JTree timeslotsTree) {
		List<Timeslot> timeslots = SwingUtils.getSelectedLeafsUserObjects(timeslotsTree, Timeslot.class);
		sendTimeslots(parent, timeslots);
	}

	/**
	 * Gets all selected timeslots that are indirectly selected by selecting the day and create a new dialog to send those timeslots.
	 * 
	 * @param parent The parent dialog
	 * @param timeslotsTree The tree with selected times
	 */
	public void transferDay(Dialog parent, JTree timeslotsTree) {
		Day day = SwingUtils.getSelectedLeafUserObject(timeslotsTree, Day.class);
		if (day != null) {
			List<Timeslot> timeslots = day.getTimeslots();
			sendTimeslots(parent, timeslots);
		}
	}

	/**
	 * Opens a new dialog and displays the sum of timeslots to send.
	 * 
	 * @param parent The parent dialogs
	 * @param timeslots The timeslots to send
	 */
	private void sendTimeslots(Dialog parent, List<Timeslot> timeslots) {
		timeslotService.validateForSending(timeslots);
		if (!CollectionUtils.isEmpty(timeslots)) {
			TimeslotSendDialog dialog = new TimeslotSendDialog(this, timeslots, parent);

			// Centering dialog on desktop screen
			SwingUtils.centerOnScreen(dialog);

			// Show dialog
			dialog.setVisible(true);
		}
	}

	/**
	 * Sends multiple timeslot in sum to Jira ticketing server. Creates worklog items in Jira.
	 * 
	 * @param timeslots The timeslots to send
	 * @param ticketname The unique ticket name
	 * @param comments Some comments to save with it
	 * @param url The url of the Jira server
	 * @param user The login name
	 * @param pass The password
	 * @param savePassword if <code>true</code> then passwort is saved in database
	 */
	public void sendTimeslot(List<Timeslot> timeslots, String ticketname, String comments, String url, String user, char[] pass, boolean savePassword) {
		JiraSettings settings = new JiraSettings(url, user, String.valueOf(pass));
		timeslotService.sendTimeslots(timeslots, ticketname, comments, settings);
		configurationService.setValue(Key.LAST_USED_URL, url);
		configurationService.setValue(Key.LAST_USED_USER, user);
		configurationService.setValue(Key.LAST_USED_TICKET, ticketname);
		configurationService.setValue(Key.LAST_USED_PASS_SAVE, Boolean.toString(savePassword));
		if (savePassword) {
			configurationService.setValue(Key.LAST_USED_PASS, String.valueOf(pass));
		} else {
			configurationService.setValue(Key.LAST_USED_PASS, "");
		}
		configurationService.addValueToList(Key.LAST_USED_TICKET_LIST, ticketname);
	}

	/**
	 * Gets the configuration value of the given key.
	 * 
	 * @param key The config key
	 * @return The value
	 */
	public Object getConfigurationValue(Key key) {
		if (key.isListKey()) {
			return configurationService.getValueList(key);
		} else {
			return configurationService.getValue(key);
		}
	}

	/**
	 * Searches in Jira database for tickets with the given search text.
	 * 
	 * @param text The text to search for
	 * @param username The username in Jira
	 * @param url The URL to Jira server
	 * @param password The password
	 * @return The list of tickets
	 */
	public List<Ticket> getTicketsBySearchText(String text, String url, String username, char[] password) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		JiraSettings settings = new JiraSettings(url, username, String.valueOf(password));
		List<String> ticketNames = jiraService.getTicketNamesBySearchText(text, settings);
		for (String name : ticketNames) {
			String desc = jiraService.getSummary(name, settings);
			tickets.add(new Ticket(name, desc));
		}
		return tickets;
	}

	/**
	 * Searches Jira for all last used tickets.
	 * 
	 * @param url The URL to Jira server
	 * @param username The username in Jira
	 * @param password The password
	 * @return The ticket list inside a ComboBox
	 */
	public JComboBox<Ticket> getLastUsedTicketsComboBox(String url, String username, char[] password) {
		final JComboBox<Ticket> comboBoxTickets = new JComboBox<Ticket>();
		if (StringUtils.isNotEmpty(url)) {
			// Fill in the last used tickets and select the very last one
			String name = (String) this.getConfigurationValue(Key.LAST_USED_TICKET);
			Ticket lastTicket = getTicket(name, url, username, password);
			comboBoxTickets.addItem(lastTicket);
			// Get all ticket names of the last used tickets
			@SuppressWarnings("unchecked")
			List<String> lastTicketNames = (List<String>) this.getConfigurationValue(Key.LAST_USED_TICKET_LIST);
			if (lastTicketNames != null) {
				for (String oneLastTicketName : lastTicketNames) {
					Ticket oneLastTicket = getTicket(oneLastTicketName, url, username, password);
					// The last ticket is ignored, because otherwise it appears twice
					if (!oneLastTicket.equals(lastTicket)) {
						comboBoxTickets.addItem(oneLastTicket);
					}
				}
			}
		}
		return comboBoxTickets;
	}

	/**
	 * Searches Jira for the given ticket.
	 * 
	 * @param name The ticket name
	 * @param url The URL to Jira server
	 * @param username The username in Jira
	 * @param password The password
	 * @return The ticket
	 */
	private Ticket getTicket(String name, String url, String username, char[] password) {
		JiraSettings settings = new JiraSettings(url, username, String.valueOf(password));
		String summary = jiraService.getSummary(name, settings);
		return new Ticket(name, summary);
	}

	/**
	 * @param inputFile
	 * @param outputFolder
	 * @param timeslots
	 * @param startRow
	 * @param columnDate
	 * @param columnStart
	 * @param columnEnd
	 * @param columnPause
	 */
	public void fillTimesheet(String inputFile, String outputFolder, List<Timeslot> timeslots, String startRow, String columnDate, String columnStart,
			String columnEnd, String columnPause) {
		try {
			Validate.notEmpty(inputFile, "Input file should not be empty");
			Validate.notEmpty(outputFolder, "Output folder should not be empty");
			Validate.notEmpty(startRow, "Start row should not be empty");
			Validate.notEmpty(columnDate, "Date column should not be empty");
			Validate.notEmpty(columnStart, "Start column should not be empty");
			Validate.notEmpty(columnEnd, "End column should not be empty");
			Validate.notEmpty(columnPause, "Pause column should not be empty");
			File inFile = new File(inputFile);
			File outFile = File.createTempFile("timesheet", ".xls", new File(outputFolder));
			TimeslotExcelWorker excelWorker = new TimeslotExcelWorker(inFile, outFile);
			excelWorker.writeTimeslots(timeslots, 0, Integer.parseInt(startRow), Integer.parseInt(columnDate), Integer.parseInt(columnStart),
					Integer.parseInt(columnEnd), Integer.parseInt(columnPause));
			excelWorker.close();
			configurationService.setValue(Key.LAST_USED_INPUT_FILE, inputFile);
			configurationService.setValue(Key.LAST_USED_OUTPUT_FOLDER, outputFolder);
			configurationService.setValue(Key.LAST_USED_STARTROW, startRow);
			configurationService.setValue(Key.LAST_USED_DATECOLUMN, columnDate);
			configurationService.setValue(Key.LAST_USED_STARTCOLUMN, columnStart);
			configurationService.setValue(Key.LAST_USED_ENDCOLUMN, columnEnd);
			configurationService.setValue(Key.LAST_USED_PAUSECOLUMN, columnPause);
		} catch (Exception e) {
			throw new GuiBusinessException(GuiBusinessException.ERROR_WRITING_EXCEL_FILE, e);
		}
	}

	/**
	 * @param parent
	 * @param timeslotsTree
	 */
	public void openTimesheetDialog(Dialog parent, JTree timeslotsTree) {
		List<Day> days = SwingUtils.getSelectedLeafsUserObjects(timeslotsTree, Day.class);
		if (!CollectionUtils.isEmpty(days)) {
			List<Timeslot> timeslots = new ArrayList<Timeslot>();
			for (Day day : days) {
				timeslots.addAll(day.getTimeslots());
			}
			TimeslotTimesheetDialog dialog = new TimeslotTimesheetDialog(this, timeslots, parent);

			// Centering dialog on desktop screen
			SwingUtils.centerOnScreen(dialog);

			// Show dialog
			dialog.setVisible(true);
		}
	}

}
