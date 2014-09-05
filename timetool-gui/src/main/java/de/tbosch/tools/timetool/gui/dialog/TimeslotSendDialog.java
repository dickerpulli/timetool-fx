package de.tbosch.tools.timetool.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.gui.model.Ticket;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.DateUtils;
import de.tbosch.tools.timetool.utils.SwingUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for adding new projects.
 * 
 * @author Thomas Bosch
 */
public class TimeslotSendDialog extends JDialog {

	private static final long serialVersionUID = -3059381707569544828L;

	private final GuiController guiController;

	private final List<Timeslot> timeslots;

	/**
	 * Constructor. Model type.
	 * 
	 * @param guiController The controller
	 * @param timeslots List of timeslots to send
	 * @param dialog The parent dialog
	 */
	public TimeslotSendDialog(GuiController guiController, List<Timeslot> timeslots, Dialog dialog) {
		super(dialog, true);
		this.guiController = guiController;
		this.timeslots = timeslots;
		setTitle(MessageHelper.getMessage("frame.transferTimeslot.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		init();
	}

	/**
	 * Adds the components to the frame.
	 */
	private void init() {
		// Main layout
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		JPanel contentPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// Content Panel

		// Layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPanel.setLayout(gridBagLayout);
		GridBagConstraints constraints = new GridBagConstraints();
		Insets insets = new Insets(5, 5, 5, 5);
		constraints.insets = insets;

		// initial row num
		int row = 0;

		// url
		JLabel labelUrl = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelUrl"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelUrl, constraints);

		constraints = new GridBagConstraints();
		String url = (String) guiController.getConfigurationValue(Key.LAST_USED_URL);
		final JTextField textFieldUrl = new JTextField(url);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(textFieldUrl, constraints);

		// next row
		row++;

		// user name
		constraints = new GridBagConstraints();
		JLabel labelUser = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelUser"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelUser, constraints);

		constraints = new GridBagConstraints();
		String user = (String) guiController.getConfigurationValue(Key.LAST_USED_USER);
		final JTextField textFieldUser = new JTextField(user);
		textFieldUser.setPreferredSize(new Dimension(100, 20));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(textFieldUser, constraints);

		// next row
		row++;

		// password
		constraints = new GridBagConstraints();
		JLabel labelPass = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelPass"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelPass, constraints);

		constraints = new GridBagConstraints();
		String pass = (String) guiController.getConfigurationValue(Key.LAST_USED_PASS);
		final JPasswordField passwordFieldPass = new JPasswordField(pass);
		passwordFieldPass.setPreferredSize(new Dimension(100, 20));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(passwordFieldPass, constraints);

		constraints = new GridBagConstraints();
		String save = (String) guiController.getConfigurationValue(Key.LAST_USED_PASS_SAVE);
		final JCheckBox checkBoxSave = new JCheckBox(MessageHelper.getMessage("frame.transferTimeslot.labelSavePassword"));
		checkBoxSave.setSelected(Boolean.parseBoolean(save));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(checkBoxSave, constraints);

		// next row
		row++;

		// Add time to send
		constraints = new GridBagConstraints();
		JLabel labelTime = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelTime"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelTime, constraints);

		constraints = new GridBagConstraints();
		Collections.sort(timeslots);
		String start = DateUtils.toDateString(timeslots.get(0).getStarttime());
		String time = DateUtils.getPeriodAsString(TimeslotUtils.getSum(timeslots));
		JLabel labelTimeString = new JLabel(start + " : " + time);
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(labelTimeString, constraints);

		// Next row
		row++;

		constraints = new GridBagConstraints();
		JLabel labelTicket = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelTicket"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelTicket, constraints);

		// text field for ticket name
		constraints = new GridBagConstraints();
		final JTextField textFieldSearch = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(textFieldSearch, constraints);

		// button to search ticket
		constraints = new GridBagConstraints();
		final JButton buttonSearch = new JButton(MessageHelper.getMessage("button.search"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 3;
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(buttonSearch, constraints);

		// Next row
		row++;

		// drop down for tickets found
		constraints = new GridBagConstraints();
		final JComboBox<Ticket> comboBoxTickets = guiController.getLastUsedTicketsComboBox(textFieldUrl.getText(), textFieldUser.getText(),
				passwordFieldPass.getPassword());
		comboBoxTickets.setPreferredSize(new Dimension(300, 20));
		comboBoxTickets.setSize(new Dimension(300, 20));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.WEST;
		contentPanel.add(comboBoxTickets, constraints);

		// Add action to search button to fill combo box
		final JDialog thisFrame = this;
		buttonSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// After the search the whole list is resetted and filled with the results of the search
				List<Ticket> list = guiController.getTicketsBySearchText(textFieldSearch.getText(), textFieldUrl.getText(), textFieldUser.getText(),
						passwordFieldPass.getPassword());
				comboBoxTickets.removeAllItems();
				for (Ticket ticket : list) {
					comboBoxTickets.addItem(ticket);
				}
				SwingUtils.centerOnScreen(thisFrame);
			}
		});
		textFieldSearch.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buttonSearch.doClick();
				}
			}
		});

		// Next row
		row++;

		constraints = new GridBagConstraints();
		JLabel labelComments = new JLabel(MessageHelper.getMessage("frame.transferTimeslot.labelComments"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = row;
		constraints.anchor = GridBagConstraints.EAST;
		contentPanel.add(labelComments, constraints);

		// text field for comments
		constraints = new GridBagConstraints();
		final JTextField textFieldComments = new JTextField();
		textFieldComments.setPreferredSize(new Dimension(200, 20));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		contentPanel.add(textFieldComments, constraints);

		// Button Panel

		// Layout
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Buttons
		JButton sendButton = new JButton(MessageHelper.getMessage("button.send"));
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.sendTimeslot(timeslots, ((Ticket) comboBoxTickets.getSelectedItem()).getName(), textFieldComments.getText(),
						textFieldUrl.getText(), textFieldUser.getText(), passwordFieldPass.getPassword(), checkBoxSave.isSelected());
				dispose();
			}
		});
		JButton cancelButton = new JButton(MessageHelper.getMessage("button.cancel"));
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(sendButton);
		buttonPanel.add(cancelButton);

		// Initiate the layout packing
		setResizable(false);
		pack();
	}

}
