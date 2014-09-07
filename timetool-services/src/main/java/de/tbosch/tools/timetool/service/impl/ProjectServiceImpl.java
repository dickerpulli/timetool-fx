package de.tbosch.tools.timetool.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.repository.ProjectRepository;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TimeslotService timeslotService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project getProject(long projectId) {
		return projectRepository.findOne(projectId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project getActiveProject() {
		return projectRepository.findByActiveTrue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveProject(long projectId) {
		List<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			project.setActive(false);
			projectRepository.save(project);
		}
		Timeslot activeTimeslot = timeslotService.getActiveTimeslot();
		if (activeTimeslot != null) {
			LogUtils.logInfo("Stop active timeslot with starttime = " + activeTimeslot.getStarttime(), LOGGER);
			timeslotService.deactivateTimeslot(activeTimeslot.getId());
		}
		Project project = projectRepository.findOne(projectId);
		project.setActive(true);
		LogUtils.logInfo("Set the project with name = " + project.getName() + " as active", LOGGER);
		projectRepository.save(project);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createProject(String name, long customerId) {
		LogUtils.logInfo("Creates a new project with name '" + name + "'", LOGGER);
		Customer customer = customerService.getCustomer(customerId);
		Project project = new Project();
		project.setCustomer(customer);
		project.setActive(false);
		project.setName(name);
		return projectRepository.save(project).getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProject(Project project) {
		Project projectEntity = projectRepository.findOne(project.getId());
		projectRepository.delete(projectEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveProject(Project project, String name, long customerId) {
		LogUtils.logInfo("Save project with new name '" + name + "' and customerId '" + customerId + "'", LOGGER);
		Customer customer = customerService.getCustomer(customerId);
		Project projectEntity = projectRepository.findOne(project.getId());
		projectEntity.setName(name);
		projectEntity.setCustomer(customer);
		projectRepository.save(projectEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullName(Project proj) {
		Project project = projectRepository.findOne(proj.getId());
		String name = project.getCustomer().getName() + " - " + project.getName();
		return name;
	}

}
