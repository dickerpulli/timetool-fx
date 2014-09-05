package de.tbosch.tools.timetool.utils.excel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import de.tbosch.tools.timetool.exception.UtilsBusinessException;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.LogUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils.Workday;

/**
 * An extension of the ExcelWriter to write {@link Timeslot}s to an Excel file.
 * 
 * @author Thomas Bosch
 */
public class TimeslotExcelWorker extends ExcelWorker {

	private static final Log LOG = LogFactory.getLog(TimeslotExcelWorker.class);

	private static final int MAX_DAYS_IN_MONTH = 31;

	/**
	 * {@inheritDoc}.
	 * 
	 * @see ExcelWorker#ExcelWorker(File)
	 */
	public TimeslotExcelWorker(File inputFile, File outputFile) {
		super(inputFile, outputFile);
	}

	/**
	 * Writes a list of timeslots to the Excel file. All existing entries will be overwritten.
	 * 
	 * @param timeslots The timeslots to write
	 * @param sheet The worksheet index
	 * @param rowBegin The row where the first timeslot will be in
	 * @param columnDate The column where the date is
	 * @param columnStarttime The column where the starttime is
	 * @param columnEndtime The column where the endtime is
	 * @param columnPause The column where the pause is
	 */
	public void writeTimeslots(List<Timeslot> timeslots, int sheet, int rowBegin, int columnDate, int columnStarttime, int columnEndtime, int columnPause) {
		List<Workday> workdays = TimeslotUtils.getWorkdays(timeslots);
		for (int row = rowBegin; row < rowBegin + MAX_DAYS_IN_MONTH; row++) {
			String dateString = read(sheet, row, columnDate);
			DateTimeFormatter formatter1 = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
			DateTimeFormatter formatter2 = new DateTimeFormatterBuilder().appendPattern("dd.MM.yyyy").toFormatter();
			List<DateTimeFormatter> formatters = Arrays.asList(formatter1, formatter2);
			boolean patternMatches = false;
			for (DateTimeFormatter formatter : formatters) {
				try {
					LocalDate date = formatter.parseDateTime(dateString).toLocalDate();
					patternMatches = true;
					int found = 0;
					for (Workday workday : workdays) {
						DateTime starttime = workday.getInterval().getStart();
						DateTime endtime = workday.getInterval().getEnd();
						String starttimeText = starttime.toString("HH:mm");
						String endtimeText = endtime.toString("HH:mm");
						LocalDate starttimeDay = starttime.toLocalDate();
						LocalDate endtimeDay = endtime.toLocalDate();
						long pauseMillis = workday.getPause().getMillis();
						DateTime pauseDateTime = new DateTime(starttimeDay.toDateTimeAtStartOfDay());
						String pause = pauseDateTime.plus(pauseMillis).toString("HH:mm");
						if (!starttimeDay.equals(endtimeDay)) {
							throw new UtilsBusinessException(UtilsBusinessException.NOT_SAME_DAY, starttimeDay, endtimeDay);
						}
						if (starttimeDay.equals(date)) {
							if (found > 0) {
								throw new UtilsBusinessException(UtilsBusinessException.TOO_MUCH_TIMESLOTS_FOR_DAY, date);
							}
							writeLabel(sheet, row, columnStarttime, starttimeText);
							writeLabel(sheet, row, columnEndtime, endtimeText);
							writeLabel(sheet, row, columnPause, pause);
							found++;
						}
					}
					if (found == 0) {
						LogUtils.logWarn("No timeslot found in the list for " + date, LOG);
					}
				} catch (IllegalArgumentException e) {
					LogUtils.logInfo("Pattern not matching, try another date pattern ...", LOG);
				}
			}
			if (!patternMatches) {
				throw new UtilsBusinessException(UtilsBusinessException.PARSE_ERROR, dateString);
			}
		}
	}
}
