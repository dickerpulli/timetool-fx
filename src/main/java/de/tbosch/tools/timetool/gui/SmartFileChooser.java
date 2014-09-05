package de.tbosch.tools.timetool.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 * The Class SmartFileChooser. A class for using extensions in the JFileChooser.
 * 
 * @author Thomas Bosch
 */
public class SmartFileChooser extends JFileChooser {

	private static final long serialVersionUID = 7856245421471797158L;

	/**
	 * The Constructor.
	 */
	public SmartFileChooser() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFileFilter(FileFilter filter) {
		File f = getSelectedFile();
		String txt = null;
		if (f != null) {
			FileChooserUI ui = this.getUI();
			if (ui instanceof BasicFileChooserUI) {
				txt = ((BasicFileChooserUI) ui).getFileName();
			}
		}
		super.setFileFilter(filter);
		if (f != null && filter != null && txt != null) {
			FileChooserUI ui = this.getUI();
			if (!(ui instanceof BasicFileChooserUI)) {
				return;
			}
			int idx = txt.lastIndexOf(".");
			if (idx != -1) {
				txt = txt.substring(0, idx + 1);
				if (filter instanceof GenericFileFilter) {
					txt += ((GenericFileFilter) filter).getExtension();
				}
			}
			setSelectedFile(new File(txt));
			((BasicFileChooserUI) ui).setFileName(txt);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getSelectedFile() {
		File f = super.getSelectedFile();

		// extends the default extension of the file filter is a GenericFileFilter
		// otherwise don't extend anything
		if (f != null) {
			String ext = ".*";
			if (getFileFilter() instanceof GenericFileFilter) {
				ext = ((GenericFileFilter) getFileFilter()).getExtension();
				if (!f.getName().substring(f.getName().length() - 4, f.getName().length()).equals("." + ext)) {
					File nf = new File(f.getAbsolutePath() + "." + ext);
					return nf;
				}
			}
		}

		return f;
	}
}
