package de.tbosch.tools.timetool.gui.dialog;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.WindowConstants;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * Frame for adding new projects.
 * 
 * @author Thomas Bosch
 */
public class TimeslotEditDialog extends JDialog {

	private static final long serialVersionUID = -3059381707569544828L;

	private GuiController guiController;

	private Timeslot timeslot;

	/**
	 * Constructor. Model type.
	 * 
	 * @param guiController The controller
	 * @param timeslot The timeslot to edit
	 * @param dialog The parent dialog
	 */
	public TimeslotEditDialog(GuiController guiController, Timeslot timeslot, Dialog dialog) {
		super(dialog, true);
		this.guiController = guiController;
		this.timeslot = timeslot;
		setTitle(MessageHelper.getMessage("frame.editTimeslot.title"));
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
		JLabel labelStarttime = new JLabel(MessageHelper.getMessage("frame.editTimeslot.labelStarttime"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.EAST;
		add(labelStarttime, constraints);

		// Date field for starttime
		constraints = new GridBagConstraints();
		SpinnerModel starttimeModel = new SpinnerDateModel();
		final JSpinner starttimeSpinner = new JSpinner(starttimeModel);
		DateEditor starttimeEditor = new DateEditor(starttimeSpinner, MessageHelper.getMessage("format.timestamp"));
		starttimeSpinner.setEditor(starttimeEditor);
		starttimeSpinner.setValue(timeslot.getStarttime());
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		add(starttimeSpinner, constraints);

		// Add label for textfield
		constraints = new GridBagConstraints();
		JLabel labelEndtime = new JLabel(MessageHelper.getMessage("frame.editTimeslot.labelEndtime"));
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		add(labelEndtime, constraints);

		// Date field for starttime
		constraints = new GridBagConstraints();
		SpinnerModel endtimeModel = new SpinnerDateModel();
		final JSpinner endtimeSpinner = new JSpinner(endtimeModel);
		DateEditor endtimeEditor = new DateEditor(endtimeSpinner, "dd.MM.yyyy HH:mm:ss");
		endtimeSpinner.setEditor(endtimeEditor);
		endtimeSpinner.setValue(timeslot.getEndtime());
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(endtimeSpinner, constraints);

		// Buttons
		constraints = new GridBagConstraints();
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton(MessageHelper.getMessage("button.ok"));
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.saveTimeslot(timeslot, (Date) starttimeSpinner.getValue(), (Date) endtimeSpinner.getValue());
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
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		add(buttonPanel, constraints);

		// Initiate the layout packing
		setResizable(false);
		pack();
	}
}
