package de.tbosch.tools.timetool.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The Hibernate implementation of the {@link GenericDao}.
 * 
 * @author Thomas Bosch
 * 
 * @param <T> The type of the object
 * @param <K> The type of the identifier
 */
public class GenericHibernateDao<T, K extends Serializable> extends HibernateDaoSupport implements GenericDao<T, K> {

	@Autowired
	private SessionFactory sessionFactory;

	private final Class<T> type;

	/**
	 * The constructor.
	 * 
	 * @param type The type of the object to do things with
	 */
	public GenericHibernateDao(Class<T> type) {
		this.type = type;
	}

	/**
	 * Is called after constructor call by Spring's lifecycle management.
	 */
	@PostConstruct
	public void postConstruct() {
		setSessionFactory(sessionFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public K create(T transientEntity) {
		return (K) getHibernateTemplate().save(transientEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T read(K id) {
		return getHibernateTemplate().get(type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T persistentEntity) {
		getHibernateTemplate().delete(persistentEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T update(T persistentEntity) {
		return getHibernateTemplate().merge(persistentEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(type);
		return criteria.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict() {
		getSessionFactory().evict(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

}
