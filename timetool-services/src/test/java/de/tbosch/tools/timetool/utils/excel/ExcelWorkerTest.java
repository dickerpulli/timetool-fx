package de.tbosch.tools.timetool.utils.excel;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author thomas.bosch
 * 
 */
public class ExcelWorkerTest {

	/**
	 * Test method for {@link de.tbosch.tools.timetool.utils.excel.ExcelWorker#writeLabel(int, int, int, java.lang.String)}.
	 */
	@Test
	public void testWriteLabel() {
		File fileFrom = FileUtils.toFile(this.getClass().getResource(("/xls/test.xls")));
		File folderTo = FileUtils.toFile(this.getClass().getResource(("/xls")));
		File fileTo = new File(folderTo, "test_output.xls");
		ExcelWorker worker = new ExcelWorker(fileFrom, fileTo);
		worker.writeLabel(0, 3, 1, "08:00");
		worker.writeLabel(0, 3, 2, "17:30");
		worker.writeLabel(0, 3, 3, "00:30");
		worker.close();
	}

	@Test
	public void testRead() {
		File fileFrom = FileUtils.toFile(this.getClass().getResource("/xls/test.xls"));
		ExcelWorker worker = new ExcelWorker(fileFrom);
		String read = worker.read(0, 4, 0);
		assertEquals("02/01/2010", read);
	}

}
