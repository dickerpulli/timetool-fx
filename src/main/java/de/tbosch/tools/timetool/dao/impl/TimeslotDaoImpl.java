package de.tbosch.tools.timetool.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import de.tbosch.tools.timetool.dao.GenericHibernateDao;
import de.tbosch.tools.timetool.dao.TimeslotDao;
import de.tbosch.tools.timetool.model.Timeslot;

/**
 * Standard Hibernate implementation of the {@link TimeslotDao}.
 * 
 * @author Thomas Bosch
 */
@Repository
public class TimeslotDaoImpl extends GenericHibernateDao<Timeslot, Long> implements TimeslotDao {

	/**
	 * The constructor.
	 */
	public TimeslotDaoImpl() {
		super(Timeslot.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timeslot findActive() {
		Criteria criteria = getSession().createCriteria(Timeslot.class);
		criteria.add(Restrictions.eq("active", true));
		return (Timeslot) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timeslot findWithTodayStarttime(long projectId) {
		Criteria criteria = getSession().createCriteria(Timeslot.class);
		criteria.add(Restrictions.eq("project.id", projectId));
		DateTime today0h = new LocalDate().toDateTimeAtStartOfDay();
		criteria.add(Restrictions.between("starttime", today0h.toDate(), today0h.plusDays(1).toDate()));
		criteria.addOrder(Order.desc("starttime"));
		criteria.setMaxResults(1);
		return (Timeslot) criteria.uniqueResult();
	}
}
