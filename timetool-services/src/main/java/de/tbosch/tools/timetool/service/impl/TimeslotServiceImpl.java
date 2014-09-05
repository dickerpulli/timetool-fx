package de.tbosch.tools.timetool.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.dao.TimeslotDao;
import de.tbosch.tools.timetool.exception.ServiceBusinessException;
import de.tbosch.tools.timetool.model.JiraSettings;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.JiraService;
import de.tbosch.tools.timetool.service.ProjectService;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.DateUtils;
import de.tbosch.tools.timetool.utils.LogUtils;
import de.tbosch.tools.timetool.utils.TimeslotUtils;

/**
 * Standard implementation of the {@link TimeslotService}.
 * 
 * @author Thomas Bosch
 */
@Service
@Transactional
public class TimeslotServiceImpl implements TimeslotService {

	/** The logger. */
	private static final Log LOG = LogFactory.getLog(TimeslotServiceImpl.class);

	@Autowired
	private TimeslotDao timeslotDao;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private JiraService jiraService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Timeslot> getAllTimeslots() {
		return timeslotDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activateTimeslot(long projectId) {
		Timeslot timeslot = getActiveTimeslot();
		if (timeslot != null) {
			LogUtils.logWarn("Active timeslot found - we do not create new one", LOG);
		} else {
			// no active timeslot found, create a new one
			LogUtils.logInfo("Create new timeslot for projectId = " + projectId, LOG);
			timeslot = new Timeslot();
			timeslot.setStarttime(new Date());
			timeslot.setEndtime(new Date());
			timeslot.setProject(projectService.getProject(projectId));
			timeslot.setActive(true);
			timeslotDao.create(timeslot);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivateTimeslot(long timeslotId) {
		Timeslot timeslot = timeslotDao.read(timeslotId);
		if (timeslot != null) {
			LogUtils.logInfo("Update endtime for timeslot, timeslotId = " + timeslotId, LOG);
			timeslot.setActive(false);
			timeslotDao.update(timeslot);
		} else {
			LogUtils.logError("Timeslot could not be ended", LOG);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timeslot getTimeslot(long timeslotId) {
		return timeslotDao.read(timeslotId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timeslot getActiveTimeslot() {
		return timeslotDao.findActive();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<Timeslot>> getAllTimeslotsPerProject() {
		Map<String, List<Timeslot>> perProject = new LinkedHashMap<String, List<Timeslot>>();
		List<Timeslot> allTimeslots = getAllTimeslots();
		for (Timeslot timeslot : allTimeslots) {
			String key = projectService.getFullName(timeslot.getProject());
			List<Timeslot> timeslots = perProject.get(key);
			if (timeslots == null) {
				timeslots = new ArrayList<Timeslot>();
				perProject.put(key, timeslots);
			}
			timeslots.add(timeslot);
		}
		return perProject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timeslot updateEndtime(Timeslot ts) {
		Timeslot timeslot = timeslotDao.read(ts.getId());
		Date now = new Date();
		if (now.after(timeslot.getEndtime())) {
			// Test difference, if the difference is bigger than 5 minutes
			// the computer was shut down / hibernated
			if (DateUtils.getDifferenceInMinutes(timeslot.getEndtime(), now) > 5) {
				LogUtils.logWarn("The time 'now' is more than 5 minutes later than the timeslot endtime, maybe computer was shut down", LOG);
				return null;
			}
			timeslot.setEndtime(now);
			return timeslotDao.update(timeslot);
		} else {
			LogUtils.logError("Endtime is changed to a time in the past - last endtime = " + timeslot.getEndtime() + ", now = " + now, LOG);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivateActiveTimeslot() {
		Timeslot timeslot = getActiveTimeslot();
		if (timeslot != null) {
			LogUtils.logInfo("Deactivate active timeslot - timeslot starttime=" + timeslot.getStarttime(), LOG);
			timeslot.setActive(false);
			timeslotDao.update(timeslot);
		} else {
			LogUtils.logInfo("No active timeslot found", LOG);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteTimeslot(Timeslot ts) {
		LogUtils.logInfo("Delete timeslot for projectId = " + ts.getProject() + " and starttime = " + ts.getStarttime(), LOG);
		Timeslot timeslot = timeslotDao.read(ts.getId());
		timeslotDao.delete(timeslot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndtime(Timeslot timeslot, Date endtime) {
		// Check if the new endtime is after the old starttime
		if (endtime.before(timeslot.getStarttime())) {
			throw new ServiceBusinessException(ServiceBusinessException.ENDTIME_BEFORE_STARTTIME);
		}
		LogUtils.logInfo(
				"Set new endtime for timeslot, old = " + DateUtils.toDateTimeString(timeslot.getEndtime()) + ", new = " + DateUtils.toDateTimeString(endtime),
				LOG);
		timeslot = timeslotDao.read(timeslot.getId());
		timeslot.setEndtime(endtime);
		timeslotDao.update(timeslot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStarttime(Timeslot timeslot, Date starttime) {
		// Check if the new starttime is before the old endtime
		if (starttime.after(DateUtils.roundUpToSeconds(timeslot.getEndtime()))) {
			throw new ServiceBusinessException(ServiceBusinessException.STARTTIME_AFTER_ENDTIME);
		}
		LogUtils.logInfo(
				"Set new starttime for timeslot, old = " + DateUtils.toDateTimeString(timeslot.getStarttime()) + ", new = "
						+ DateUtils.toDateTimeString(starttime), LOG);
		timeslot = timeslotDao.read(timeslot.getId());
		timeslot.setStarttime(starttime);
		timeslotDao.update(timeslot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveTimeslot(Timeslot timeslot, Date starttime, Date endtime) {
		// Check if the new endtime is after the old starttime
		if (endtime.before(starttime)) {
			throw new ServiceBusinessException(ServiceBusinessException.ENDTIME_BEFORE_STARTTIME);
		}
		LogUtils.logInfo(
				"Set new starttime for timeslot, old = " + DateUtils.toDateTimeString(timeslot.getStarttime()) + ", new = "
						+ DateUtils.toDateTimeString(starttime), LOG);
		timeslot = timeslotDao.read(timeslot.getId());
		timeslot.setStarttime(starttime);
		timeslot.setEndtime(endtime);
		timeslotDao.update(timeslot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addTimeslots(List<Timeslot> timeslots) {
		Period periodSum = TimeslotUtils.getSum(timeslots);
		return DateUtils.getPeriodAsString(periodSum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleMarking(List<Timeslot> timeslots) {
		for (Timeslot timeslot : timeslots) {
			markTimeslot(timeslot, !timeslot.isMarked());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendTimeslots(List<Timeslot> timeslots, String ticketname, String comments, JiraSettings settings) {
		Collections.sort(timeslots);
		DateTime startDate = LocalDateTime.fromDateFields(timeslots.get(0).getStarttime()).toDateTime();
		int minutesSpent = TimeslotUtils.getSum(timeslots).toStandardMinutes().getMinutes();
		jiraService.addWorklog(ticketname, startDate, minutesSpent, comments, settings);
		for (Timeslot timeslot : timeslots) {
			markTimeslot(timeslot, true);
		}
	}

	/**
	 * Sets the 'marked' flag to the given value.
	 * 
	 * @param timeslot The timeslot to edit
	 * @param marked The value to set
	 */
	private void markTimeslot(Timeslot timeslot, boolean marked) {
		timeslot.setMarked(marked);
		Timeslot read = timeslotDao.read(timeslot.getId());
		read.setMarked(marked);
		timeslotDao.update(read);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateForSending(List<Timeslot> timeslots) {
		Set<LocalDate> dates = new HashSet<LocalDate>();
		for (Timeslot timeslot : timeslots) {
			if (timeslot.isMarked()) {
				throw new ServiceBusinessException(ServiceBusinessException.TIMESLOT_ALREADY_SENT);
			}
			if (dates.size() == 1 && !dates.contains(new LocalDate(timeslot.getStarttime().getTime()))) {
				throw new ServiceBusinessException(ServiceBusinessException.DIFFERENT_DATES_IN_LIST);
			} else {
				dates.add(new LocalDate(timeslot.getStarttime().getTime()));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleTimeslotActivation() {
		Timeslot activeTimeslot = getActiveTimeslot();
		if (activeTimeslot == null) {
			// no active timeslot found, take active project and start timeslot
			Project activeProject = projectService.getActiveProject();
			if (activeProject == null) {
				// uups, no active timeslot found
				LogUtils.logError("No active project found to start a timeslot", LOG);
				throw new ServiceBusinessException(ServiceBusinessException.NO_ACTIVE_PROJECT_FOUND);
			} else {
				activateTimeslot(activeProject.getId());
			}
		} else {
			// active timeslot found, deativate/end it
			deactivateTimeslot(activeTimeslot.getId());
		}
	}

}
