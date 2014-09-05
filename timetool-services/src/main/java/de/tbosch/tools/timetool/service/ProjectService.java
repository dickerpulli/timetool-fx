package de.tbosch.tools.timetool.service;

import java.util.List;

import de.tbosch.tools.timetool.model.Project;

/**
 * Service for accessing project entities.
 * 
 * @author Thomas Bosch
 */
public interface ProjectService {

	/**
	 * Gets the project with the given id.
	 * 
	 * @param projectId The identifier
	 * @return The project
	 */
	Project getProject(long projectId);

	/**
	 * Gets the actual as <b>active</b> marked project.
	 * 
	 * @return The project
	 */
	Project getActiveProject();

	/**
	 * Mark the project as <b>active</b>.
	 * 
	 * @param projectId The id of the project
	 */
	void setActiveProject(long projectId);

	/**
	 * Gets all known projects.
	 * 
	 * @return The list
	 */
	List<Project> getAllProjects();

	/**
	 * Creates a new Project with the given name.
	 * 
	 * @param name The name of the project
	 * @param customerId The id of the customer
	 * @return The id of the created object
	 */
	long createProject(String name, long customerId);

	/**
	 * Deletes the given project.
	 * 
	 * @param project The project
	 */
	void deleteProject(Project project);

	/**
	 * Saves the project with the id.
	 * 
	 * @param project The project
	 * @param text The name
	 * @param customerId The customer
	 */
	void saveProject(Project project, String text, long customerId);

	/**
	 * Gets the full name of the project. It is a concatination of customers name and projects name.
	 * 
	 * @param project The project
	 * @return The full name
	 */
	String getFullName(Project project);

}
