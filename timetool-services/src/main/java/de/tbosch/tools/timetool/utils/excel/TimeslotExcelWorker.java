package de.tbosch.tools.timetool.utils.excel;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeslotExcelWorker.class);

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
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			List<DateTimeFormatter> formatters = Arrays.asList(formatter1, formatter2);
			boolean patternMatches = false;
			for (DateTimeFormatter formatter : formatters) {
				try {
					LocalDate date = LocalDate.from(formatter.parse(dateString));
					patternMatches = true;
					int found = 0;
					for (Workday workday : workdays) {
						LocalDateTime starttime = workday.getInterval().getStart();
						LocalDateTime endtime = workday.getInterval().getEnd();
						String starttimeText = starttime.format(DateTimeFormatter.ofPattern("HH:mm"));
						String endtimeText = endtime.format(DateTimeFormatter.ofPattern("HH:mm"));
						LocalDate starttimeDay = starttime.toLocalDate();
						LocalDate endtimeDay = endtime.toLocalDate();
						long pauseNanos = workday.getPause().getNano();
						LocalDateTime pauseDateTime = LocalDateTime.from(starttimeDay.atStartOfDay());
						String pause = pauseDateTime.plusNanos(pauseNanos).format(DateTimeFormatter.ofPattern("HH:mm"));
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
						LogUtils.logWarn("No timeslot found in the list for " + date, LOGGER);
					}
				} catch (DateTimeParseException e) {
					LogUtils.logInfo("Pattern not matching, try another date pattern ...", LOGGER);
				}
			}
			if (!patternMatches) {
				throw new UtilsBusinessException(UtilsBusinessException.PARSE_ERROR, dateString);
			}
		}
	}
}
