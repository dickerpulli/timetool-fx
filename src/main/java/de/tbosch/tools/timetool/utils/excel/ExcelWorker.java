package de.tbosch.tools.timetool.utils.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.tbosch.tools.timetool.exception.UtilsBusinessException;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * A helper class to work with XLS (Excel) files.
 * 
 * @author Thomas Bosch
 */
public class ExcelWorker {

	/** The logger. */
	private static final Log LOG = LogFactory.getLog(ExcelWorker.class);

	/** The file to read from. */
	private File inputFile;

	/** The file to write to. */
	private File outputFile;

	/** The workbook to write in. */
	private WritableWorkbook writableWorkbook;

	/** The workbook to read from. */
	private Workbook readableWorkbook;

	/**
	 * Contructor with the files to read and write.
	 * 
	 * @param inputFile The Excel file to read from
	 * @param outputFile The file where the changes
	 */
	public ExcelWorker(File inputFile, File outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		initWorkbook();
	}

	/**
	 * Contructor with the file to read.
	 * 
	 * @param inputFile The Excel file to read from
	 */
	public ExcelWorker(File inputFile) {
		this.inputFile = inputFile;
		initWorkbook();
	}

	/**
	 * Initializes the workbook.
	 */
	private void initWorkbook() {
		try {
			readableWorkbook = Workbook.getWorkbook(inputFile);
			if (outputFile != null) {
				writableWorkbook = Workbook.createWorkbook(outputFile, readableWorkbook, getWorkbookSettings());
				LogUtils.logInfo("The workbook inside the new worker is readable and writeable", LOG);
				LogUtils.logInfo("A workbook for writing was created in file " + outputFile.getAbsolutePath(), LOG);
			} else {
				LogUtils.logInfo("The workbook inside the new worker is just readable", LOG);
			}
		} catch (BiffException e) {
			throw new UtilsBusinessException(UtilsBusinessException.EXCEL_ERROR, e);
		} catch (IOException e) {
			throw new UtilsBusinessException(UtilsBusinessException.IO_ERROR, outputFile.getName() + ", " + inputFile.getName(), e);
		}
	}

	/**
	 * Closes the writer and frees the output and input file.
	 */
	public void close() {
		try {
			writableWorkbook.write();
			writableWorkbook.close();
			LogUtils.logInfo("Closing workbook was successful", LOG);
		} catch (IOException e) {
			throw new UtilsBusinessException(UtilsBusinessException.IO_ERROR, outputFile.getName() + ", " + inputFile.getName(), e);
		} catch (WriteException e) {
			throw new UtilsBusinessException(UtilsBusinessException.WRITE_ERROR, outputFile.getName(), e);
		}
	}

	/**
	 * This will write a label (normal text) to the given cell in the given sheet number. A possible existing value will be overwritten.
	 * 
	 * @param sheetIndex The index of the sheet
	 * @param row The row index
	 * @param column The column index
	 * @param value The value to write to the cell
	 */
	public void writeLabel(int sheetIndex, int row, int column, String value) {
		Validate.notNull(writableWorkbook, "The worker has only a readable workbook");
		try {
			WritableSheet sheet = writableWorkbook.getSheet(sheetIndex);
			Label label = new Label(column, row, value);
			label.setString(value);

			// TODO: Copy format from original read file

			WritableCellFormat format = new WritableCellFormat();
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			format.setAlignment(Alignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.BLACK);

			label.setCellFormat(format);
			sheet.addCell(label);
			LogUtils.logDebug("Written value " + value + " to row " + row + " and column " + column + " on sheet " + sheetIndex, LOG);
		} catch (RowsExceededException e) {
			throw new UtilsBusinessException(UtilsBusinessException.EXCEL_ERROR, e);
		} catch (WriteException e) {
			throw new UtilsBusinessException(UtilsBusinessException.WRITE_ERROR, outputFile.getName(), e);
		}
	}

	/**
	 * This will read a label (normal text) from the given cell in the given sheet number.
	 * 
	 * @param sheetIndex The index of the sheet
	 * @param row The row index
	 * @param column The column index
	 * @return The value
	 */
	public String read(int sheetIndex, int row, int column) {
		Sheet sheet = readableWorkbook.getSheet(sheetIndex);
		Cell cell = sheet.getCell(column, row);
		return cell.getContents();
	}

	/**
	 * Settings for editing and reading workbooks.
	 * 
	 * @return The settings
	 */
	private WorkbookSettings getWorkbookSettings() {
		// Workbook Setting für besser Performance
		WorkbookSettings settings = new WorkbookSettings();

		// Array Grow-Size aud 128K statt 1MB
		settings.setArrayGrowSize(1024 * 128);

		// File Site auf 128K statt 5MB
		settings.setInitialFileSize(1024 * 128);

		// GC von JExcel Disable
		settings.setGCDisabled(true);

		// Keine Drawings
		settings.setDrawingsDisabled(true);

		// Cell-Validierung ausschalten
		settings.setCellValidationDisabled(true);

		return settings;
	}

}
