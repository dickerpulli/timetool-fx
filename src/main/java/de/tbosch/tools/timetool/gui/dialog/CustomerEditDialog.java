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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.hibernate.validator.InvalidStateException;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.utils.ValidationUtils;
import de.tbosch.tools.timetool.utils.ValidationUtils.InputFieldData;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for adding new customers.
 * 
 * @author Thomas Bosch
 */
public class CustomerEditDialog extends JDialog {

	private static final long serialVersionUID = 14633808809997205L;

	private GuiController guiController;

	private Customer customer;

	private Map<InputFieldData, JComponent> inputFields = new HashMap<InputFieldData, JComponent>();

	/**
	 * Constructor. Model type.
	 * 
	 * @param guiController The controller
	 * @param customer The customer to edit
	 * @param dialog The parent dialog
	 */
	public CustomerEditDialog(GuiController guiController, Customer customer, Dialog dialog) {
		super(dialog, true);
		this.guiController = guiController;
		this.customer = customer;
		setTitle(MessageHelper.getMessage("frame.editCustomer.title"));
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
		JLabel labelName = new JLabel(MessageHelper.getMessage("frame.editCustomer.labelName"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.EAST;
		add(labelName, constraints);

		// Add text field for the name
		constraints = new GridBagConstraints();
		final JTextField textField = new JTextField();
		inputFields.put(new InputFieldData(Customer.class, "name"), textField);
		textField.setPreferredSize(new Dimension(200, 24));
		textField.setText(customer.getName());
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(textField, constraints);

		// OK and Cancel buttons in extra frame
		constraints = new GridBagConstraints();
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton(MessageHelper.getMessage("button.ok"));
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiController.saveCustomer(customer.getId(), textField.getText());
					dispose();
				} catch (InvalidStateException e1) {
					ValidationUtils.notifyInputFields((InvalidStateException) e1, inputFields);
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
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.EAST;
		add(buttonPanel, constraints);

		// Initiate the layout packing
		setResizable(false);
		pack();
	}

}
