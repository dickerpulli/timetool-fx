package de.tbosch.tools.timetool.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import de.tbosch.tools.timetool.dao.ConfigurationDao;
import de.tbosch.tools.timetool.dao.GenericHibernateDao;
import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;

/**
 * Standard Hibernate implementation of the {@link ConfigurationDao}.
 * 
 * @author Thomas Bosch
 */
@Repository
public class ConfigurationDaoImpl extends GenericHibernateDao<Configuration, Long> implements ConfigurationDao {

	/**
	 * The constructor.
	 */
	public ConfigurationDaoImpl() {
		super(Configuration.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findValue(Key key) {
		Configuration configuration = findByKey(key);
		return configuration == null ? null : configuration.getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Configuration findByKey(Key key) {
		Criteria criteria = getSession().createCriteria(Configuration.class);
		criteria.add(Restrictions.eq("key", key));
		Configuration configuration = (Configuration) criteria.uniqueResult();
		return configuration;
	}

}
