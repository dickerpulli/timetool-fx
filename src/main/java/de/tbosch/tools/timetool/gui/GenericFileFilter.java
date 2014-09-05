package de.tbosch.tools.timetool.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * The Class GenericFileFilter.
 * 
 * @author Thomas Bosch
 */
public class GenericFileFilter extends FileFilter {

	/** The extension. */
	private String extension = null;

	/** The description. */
	private String description = null;

	/**
	 * The Constructor.
	 * 
	 * @param description the description
	 * @param ext the ext
	 */
	public GenericFileFilter(String description, String ext) {
		this.extension = ext;
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(File file) {
		String ext = file.getName();
		int i = ext.lastIndexOf('.');
		if (i > 0 && i < ext.length() - 1) {
			ext = ext.substring(i + 1).toLowerCase();
		}
		if (ext.equals(this.extension)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the extension.
	 * 
	 * @return the extension
	 */
	public String getExtension() {
		return this.extension;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

}
