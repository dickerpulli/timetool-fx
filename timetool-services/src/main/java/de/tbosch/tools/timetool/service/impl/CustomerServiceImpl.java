package de.tbosch.tools.timetool.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.dao.CustomerDao;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.service.CustomerService;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * Standard implementation of the CustomerService.
 * 
 * @author Thomas Bosch
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	/** The logger. */
	private static final Log LOG = LogFactory.getLog(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao customerDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer getCustomer(long customerId) {
		return customerDao.read(customerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAllCustomerNames() {
		List<String> names = new ArrayList<String>();
		List<Customer> customers = customerDao.findAll();
		for (Customer customer : customers) {
			names.add(customer.getName());
		}
		return names;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return customerDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCustomer(String name) {
		LogUtils.logInfo("Create a new customer with name '" + name + "'", LOG);
		Customer customer = new Customer();
		customer.setName(name);
		return customerDao.create(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteCustomer(Customer customer) {
		Customer customerEntity = customerDao.read(customer.getId());
		LogUtils.logInfo("Delete customer with name '" + customerEntity.getName() + "'", LOG);
		customerDao.delete(customerEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveCustomer(long id, String name) {
		LogUtils.logInfo("Save customer with new name '" + name + "'", LOG);
		Customer customer = customerDao.read(id);
		customer.setName(name);
		customerDao.update(customer);
	}

}
