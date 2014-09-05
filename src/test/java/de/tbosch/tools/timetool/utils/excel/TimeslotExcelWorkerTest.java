/**
 * 
 */
package de.tbosch.tools.timetool.utils.excel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import de.tbosch.tools.timetool.model.Timeslot;

/**
 * @author Thomas Bosch
 */
public class TimeslotExcelWorkerTest {

	/**
	 * Test method for {@link de.tbosch.tools.timetool.utils.excel.TimeslotExcelWorker#writeTimeslots(java.util.List, int, int, int, int, int, int)}.
	 */
	@Test
	public void testWriteTimeslots() {
		File fileFrom = FileUtils.toFile(this.getClass().getResource("/xls/test.xls"));
		File folderTo = FileUtils.toFile(this.getClass().getResource("/xls"));
		File fileTo = new File(folderTo, "test_output_timeslots.xls");
		TimeslotExcelWorker worker = new TimeslotExcelWorker(fileFrom, fileTo);
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStarttime(new DateTime(2010, 1, 1, 7, 30, 0, 0).toDate());
		timeslot1.setEndtime(new DateTime(2010, 1, 1, 17, 0, 0, 0).toDate());
		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStarttime(new DateTime(2010, 1, 2, 7, 30, 0, 0).toDate());
		timeslot2.setEndtime(new DateTime(2010, 1, 2, 17, 0, 0, 0).toDate());
		Timeslot timeslot31 = new Timeslot();
		timeslot31.setStarttime(new DateTime(2010, 1, 3, 7, 30, 0, 0).toDate());
		timeslot31.setEndtime(new DateTime(2010, 1, 3, 14, 0, 0, 0).toDate());
		Timeslot timeslot32 = new Timeslot();
		timeslot32.setStarttime(new DateTime(2010, 1, 3, 14, 30, 0, 0).toDate());
		timeslot32.setEndtime(new DateTime(2010, 1, 3, 17, 0, 0, 0).toDate());
		List<Timeslot> timeslots = Arrays.asList(timeslot1, timeslot2, timeslot31, timeslot32);
		worker.writeTimeslots(timeslots, 0, 3, 0, 1, 2, 3);
		worker.close();
	}

	/**
	 * Test method for {@link de.tbosch.tools.timetool.utils.excel.TimeslotExcelWorker#writeTimeslots(java.util.List, int, int, int, int, int, int)}.
	 */
	@Test
	public void testWriteTimeslotsFormatted() {
		File fileFrom = FileUtils.toFile(this.getClass().getResource("/xls/test_formatted.xls"));
		File folderTo = FileUtils.toFile(this.getClass().getResource("/xls"));
		File fileTo = new File(folderTo, "test_formatted_output_timeslots.xls");
		TimeslotExcelWorker worker = new TimeslotExcelWorker(fileFrom, fileTo);
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStarttime(new DateTime(2010, 9, 1, 7, 30, 0, 0).toDate());
		timeslot1.setEndtime(new DateTime(2010, 9, 1, 17, 0, 0, 0).toDate());
		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStarttime(new DateTime(2010, 9, 2, 7, 30, 0, 0).toDate());
		timeslot2.setEndtime(new DateTime(2010, 9, 2, 17, 0, 0, 0).toDate());
		Timeslot timeslot31 = new Timeslot();
		timeslot31.setStarttime(new DateTime(2010, 9, 3, 7, 30, 0, 0).toDate());
		timeslot31.setEndtime(new DateTime(2010, 9, 3, 14, 0, 0, 0).toDate());
		Timeslot timeslot32 = new Timeslot();
		timeslot32.setStarttime(new DateTime(2010, 9, 3, 14, 30, 0, 0).toDate());
		timeslot32.setEndtime(new DateTime(2010, 9, 3, 17, 0, 0, 0).toDate());
		List<Timeslot> timeslots = Arrays.asList(timeslot1, timeslot2, timeslot31, timeslot32);
		worker.writeTimeslots(timeslots, 0, 14, 1, 3, 4, 5);
		worker.close();
	}

}
