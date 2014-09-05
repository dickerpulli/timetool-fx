package de.tbosch.tools.timetool.dao;

import de.tbosch.tools.timetool.model.Project;

/**
 * DAO interface to access {@link Project} objects.
 * 
 * @author Thomas Bosch
 */
public interface ProjectDao extends GenericDao<Project, Long> {

	/**
	 * Finds the active project. There could only be one.
	 * 
	 * @return The active project
	 */
	Project findActive();

}
