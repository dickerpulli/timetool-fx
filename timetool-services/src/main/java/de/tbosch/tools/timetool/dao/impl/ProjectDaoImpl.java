package de.tbosch.tools.timetool.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tbosch.tools.timetool.dao.GenericHibernateDao;
import de.tbosch.tools.timetool.dao.ProjectDao;
import de.tbosch.tools.timetool.model.Project;

/**
 * Standard Hibernate implementation of the {@link ProjectDao}.
 * 
 * @author Thomas Bosch
 */
@Repository
public class ProjectDaoImpl extends GenericHibernateDao<Project, Long> implements ProjectDao {

	/**
	 * The constructor.
	 */
	public ProjectDaoImpl() {
		super(Project.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project findActive() {
		Criteria criteria = getSession().createCriteria(Project.class);
		criteria.add(Restrictions.eq("active", true));
		return (Project) criteria.uniqueResult();
	}

}
