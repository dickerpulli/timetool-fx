package de.tbosch.tools.timetool.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.repository.CustomerRepository;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer getCustomer(long customerId) {
		return customerRepository.findOne(customerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAllCustomerNames() {
		List<String> names = new ArrayList<String>();
		List<Customer> customers = customerRepository.findAll();
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
		return customerRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCustomer(String name) {
		LogUtils.logInfo("Create a new customer with name '" + name + "'", LOGGER);
		Customer customer = new Customer();
		customer.setName(name);
		return customerRepository.save(customer).getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteCustomer(Customer customer) {
		Customer customerEntity = customerRepository.findOne(customer.getId());
		LogUtils.logInfo("Delete customer with name '" + customerEntity.getName() + "'", LOGGER);
		customerRepository.delete(customerEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveCustomer(long id, String name) {
		LogUtils.logInfo("Save customer with new name '" + name + "'", LOGGER);
		Customer customer = customerRepository.findOne(id);
		customer.setName(name);
		customerRepository.save(customer);
	}

}
