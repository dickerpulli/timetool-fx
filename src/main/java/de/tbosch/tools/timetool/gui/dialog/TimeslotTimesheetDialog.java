package de.tbosch.tools.timetool.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.tbosch.tools.timetool.controller.GuiController;
import de.tbosch.tools.timetool.gui.GenericFileFilter;
import de.tbosch.tools.timetool.gui.SmartFileChooser;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * 
 * @author Thomas Bosch
 */
public class TimeslotTimesheetDialog extends JDialog {

	private static final long serialVersionUID = -3059381764297654828L;

	private List<Timeslot> timeslots;

	private GuiController guiController;

	/**
	 * Constructor.
	 * 
	 * @param guiController The GUI controller
	 * @param timeslots The timeslots to write
	 * @param parent The parent dialog
	 */
	public TimeslotTimesheetDialog(GuiController guiController, List<Timeslot> timeslots, Dialog parent) {
		super(parent);
		this.timeslots = timeslots;
		this.guiController = guiController;
		setTitle(MessageHelper.getMessage("frame.timesheet.title"));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		init();
	}

	/**
	 * Initialize.
	 */
	private void init() {
		setLayout(new BorderLayout());
		JPanel panelContent = new JPanel();
		JPanel panelButtons = new JPanel();
		add(panelContent, BorderLayout.CENTER);
		add(panelButtons, BorderLayout.SOUTH);

		// Content
		Insets insets = new Insets(5, 5, 5, 5);
		panelContent.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel labelStartrow = new JLabel(MessageHelper.getMessage("frame.timesheet.startrow"));
		constraints.insets = insets;
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelStartrow, constraints);
		String startrow = (String) guiController.getConfigurationValue(Key.LAST_USED_STARTROW);
		final JTextField textFieldStartrow = new JTextField(startrow, 10);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(textFieldStartrow, constraints);
		constraints.gridwidth = 1;

		JLabel labelDateColumn = new JLabel(MessageHelper.getMessage("frame.timesheet.datecolumn"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelDateColumn, constraints);
		String datecolumn = (String) guiController.getConfigurationValue(Key.LAST_USED_DATECOLUMN);
		final JTextField textFieldDateColumn = new JTextField(datecolumn, 10);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(textFieldDateColumn, constraints);
		constraints.gridwidth = 1;

		JLabel labelStartColumn = new JLabel(MessageHelper.getMessage("frame.timesheet.startcolumn"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelStartColumn, constraints);
		String startcolumn = (String) guiController.getConfigurationValue(Key.LAST_USED_STARTROW);
		final JTextField textFieldStartColumn = new JTextField(startcolumn, 10);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(textFieldStartColumn, constraints);
		constraints.gridwidth = 1;

		JLabel labelEndColumn = new JLabel(MessageHelper.getMessage("frame.timesheet.endcolumn"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelEndColumn, constraints);
		String endcolumn = (String) guiController.getConfigurationValue(Key.LAST_USED_ENDCOLUMN);
		final JTextField textFieldEndColumn = new JTextField(endcolumn, 10);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(textFieldEndColumn, constraints);
		constraints.gridwidth = 1;

		JLabel labelPauseColumn = new JLabel(MessageHelper.getMessage("frame.timesheet.pausecolumn"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelPauseColumn, constraints);
		String pausecolumn = (String) guiController.getConfigurationValue(Key.LAST_USED_PAUSECOLUMN);
		final JTextField textFieldPauseColumn = new JTextField(pausecolumn, 10);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(textFieldPauseColumn, constraints);
		constraints.gridwidth = 1;

		JLabel labelFile = new JLabel(MessageHelper.getMessage("frame.timesheet.inputfile"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelFile, constraints);
		String inputfile = (String) guiController.getConfigurationValue(Key.LAST_USED_INPUT_FILE);
		final JTextField textFieldFile = new JTextField(inputfile, 20);
		textFieldFile.setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		panelContent.add(textFieldFile, constraints);
		JButton buttonFile = new JButton(MessageHelper.getMessage("button.search"));
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(buttonFile, constraints);
		constraints.gridwidth = 1;

		JLabel labelFolder = new JLabel(MessageHelper.getMessage("frame.timesheet.outputfolder"));
		constraints.anchor = GridBagConstraints.EAST;
		panelContent.add(labelFolder, constraints);
		String outputfolder = (String) guiController.getConfigurationValue(Key.LAST_USED_OUTPUT_FOLDER);
		final JTextField textFieldFolder = new JTextField(outputfolder, 20);
		textFieldFolder.setEditable(false);
		constraints.anchor = GridBagConstraints.WEST;
		panelContent.add(textFieldFolder, constraints);
		JButton buttonFolder = new JButton(MessageHelper.getMessage("button.search"));
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panelContent.add(buttonFolder, constraints);
		constraints.gridwidth = 1;

		// Actions
		final JDialog thisDialog = this;
		buttonFile.addActionListener(new ActionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				SmartFileChooser fileChooser = new SmartFileChooser();
				fileChooser.setDialogTitle("");
				fileChooser.setFileFilter(new GenericFileFilter("*.xls (Excel)", "xls"));
				int state = fileChooser.showOpenDialog(thisDialog);
				if (state == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					textFieldFile.setText(file.getAbsolutePath());
				}
			}
		});
		buttonFolder.addActionListener(new ActionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				SmartFileChooser fileChooser = new SmartFileChooser();
				fileChooser.setDialogTitle("");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int state = fileChooser.showOpenDialog(thisDialog);
				if (state == JFileChooser.APPROVE_OPTION) {
					File folder = fileChooser.getSelectedFile();
					textFieldFolder.setText(folder.getAbsolutePath());
				}
			}
		});

		// Control buttons
		panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonCancel = new JButton(MessageHelper.getMessage("button.cancel"));
		panelButtons.add(buttonCancel);
		JButton buttonOk = new JButton(MessageHelper.getMessage("button.ok"));
		panelButtons.add(buttonOk);

		// Actions
		buttonOk.addActionListener(new ActionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.fillTimesheet(textFieldFile.getText(), textFieldFolder.getText(), timeslots, textFieldStartrow.getText(),
						textFieldDateColumn.getText(), textFieldStartColumn.getText(), textFieldEndColumn.getText(), textFieldPauseColumn.getText());
				dispose();
			}
		});
		buttonCancel.addActionListener(new ActionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// finalize
		pack();
	}

}
