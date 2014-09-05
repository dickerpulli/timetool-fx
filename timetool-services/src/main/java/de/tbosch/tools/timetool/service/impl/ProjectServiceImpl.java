package de.tbosch.tools.timetool.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.dao.ProjectDao;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.CustomerService;
import de.tbosch.tools.timetool.service.ProjectService;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * Standard implementation of the {@link ProjectService}.
 * 
 * @author Thomas Bosch
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	/** The logger. */
	private static final Log LOG = LogFactory.getLog(ProjectServiceImpl.class);

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TimeslotService timeslotService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project getProject(long projectId) {
		return projectDao.read(projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project getActiveProject() {
		return projectDao.findActive();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveProject(long projectId) {
		List<Project> projects = projectDao.findAll();
		for (Project project : projects) {
			project.setActive(false);
			projectDao.update(project);
		}
		Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
		if (activeTimeslot != null) {
			LogUtils.logInfo("Stop active timeslot with starttime = " + activeTimeslot.getStarttime(), LOG);
			timeslotService.deactivateTimeslot(activeTimeslot.getId());
		}
		Project project = projectDao.read(projectId);
		project.setActive(true);
		LogUtils.logInfo("Set the project with name = " + project.getName() + " as active", LOG);
		projectDao.update(project);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Project> getAllProjects() {
		return projectDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createProject(String name, long customerId) {
		LogUtils.logInfo("Creates a new project with name '" + name + "'", LOG);
		Customer customer = customerService.getCustomer(customerId);
		Project project = new Project();
		project.setCustomer(customer);
		project.setActive(false);
		project.setName(name);
		return projectDao.create(project);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProject(Project project) {
		Project projectEntity = projectDao.read(project.getId());
		projectDao.delete(projectEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveProject(Project project, String name, long customerId) {
		LogUtils.logInfo("Save project with new name '" + name + "' and customerId '" + customerId + "'", LOG);
		Customer customer = customerService.getCustomer(customerId);
		Project projectEntity = projectDao.read(project.getId());
		projectEntity.setName(name);
		projectEntity.setCustomer(customer);
		projectDao.update(projectEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullName(Project proj) {
		Project project = projectDao.read(proj.getId());
		String name = project.getCustomer().getName() + " - " + project.getName();
		return name;
	}

}
