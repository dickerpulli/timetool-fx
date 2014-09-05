package de.tbosch.tools.timetool.gui.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.hibernate.validator.InvalidStateException;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.utils.ValidationUtils;
import de.tbosch.tools.timetool.utils.ValidationUtils.InputFieldData;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for adding new projects.
 * 
 * @author Thomas Bosch
 */
public class ProjectEditDialog extends JDialog {

	private static final long serialVersionUID = -3059381707569544828L;

	private final GuiController guiController;

	private final Project project;

	private final Map<InputFieldData, JComponent> inputFields = new HashMap<InputFieldData, JComponent>();

	/**
	 * Constructor. Model type.
	 * 
	 * @param guiController The controller
	 * @param project The project to edit
	 * @param dialog The parent dialog
	 */
	public ProjectEditDialog(GuiController guiController, Project project, Dialog dialog) {
		super(dialog, true);
		this.guiController = guiController;
		this.project = project;
		setTitle(MessageHelper.getMessage("frame.editProject.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		init();
	}

	/**
	 * Adds the components to the frame.
	 */
	private void init() {
		// Create layout manager
		GridBagLayout gridLayout = new GridBagLayout();
		setLayout(gridLayout);

		// Add label for textfield
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel labelName = new JLabel(MessageHelper.getMessage("frame.editProject.labelName"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.EAST;
		add(labelName, constraints);

		// Add text field for the name
		constraints = new GridBagConstraints();
		final JTextField textField = new JTextField();
		inputFields.put(new InputFieldData(Project.class, "name"), textField);
		textField.setPreferredSize(new Dimension(200, 24));
		textField.setText(project.getName());
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(textField, constraints);

		// JLabel for customer choose
		constraints = new GridBagConstraints();
		JLabel labelCustomer = new JLabel(MessageHelper.getMessage("frame.editProject.labelCustomer"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		add(labelCustomer, constraints);

		// Drop down for all customers
		constraints = new GridBagConstraints();
		final JComboBox<Customer> customersComboBox = new JComboBox<Customer>(guiController.getAllCustomers().toArray(new Customer[] {}));
		customersComboBox.setSelectedItem(project.getCustomer());
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 1;
		constraints.gridx = 1;
		add(customersComboBox, constraints);

		constraints = new GridBagConstraints();
		JButton addCustomerButton = new JButton(MessageHelper.getMessage("frame.editProject.buttonManageCustomer"));
		final JDialog thisFrame = this;
		addCustomerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.manageCustomers(thisFrame);
				customersComboBox.removeAllItems();
				for (Customer customer : guiController.getAllCustomers()) {
					customersComboBox.addItem(customer);
				}
			}
		});
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 1;
		constraints.gridx = 2;
		add(addCustomerButton, constraints);

		// OK and Cancel buttons in extra frame
		constraints = new GridBagConstraints();
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton(MessageHelper.getMessage("button.ok"));
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiController.saveProject(project, textField.getText(), ((Customer) customersComboBox.getSelectedItem()).getId());
					dispose();
				} catch (InvalidStateException e1) {
					ValidationUtils.notifyInputFields(e1, inputFields);
				}
			}
		});
		JButton cancelButton = new JButton(MessageHelper.getMessage("button.cancel"));
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		add(buttonPanel, constraints);

		// Initiate the layout packing
		setResizable(false);
		pack();
	}

}
