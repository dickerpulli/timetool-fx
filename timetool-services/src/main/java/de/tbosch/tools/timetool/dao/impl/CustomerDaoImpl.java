package de.tbosch.tools.timetool.dao.impl;

import org.springframework.stereotype.Repository;

import de.tbosch.tools.timetool.dao.CustomerDao;
import de.tbosch.tools.timetool.dao.GenericHibernateDao;
import de.tbosch.tools.timetool.model.Customer;

/**
 * Standard Hibernate implementation of the {@link CustomerDao}.
 * 
 * @author Thomas Bosch
 */
@Repository
public class CustomerDaoImpl extends GenericHibernateDao<Customer, Long> implements CustomerDao {

	/**
	 * The constructor.
	 */
	public CustomerDaoImpl() {
		super(Customer.class);
	}

}
