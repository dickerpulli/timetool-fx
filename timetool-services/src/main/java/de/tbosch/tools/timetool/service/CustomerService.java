package de.tbosch.tools.timetool.service;

import java.util.List;

import de.tbosch.tools.timetool.model.Customer;

/**
 * Service for accessing customer entities.
 * 
 * @author Thomas Bosch
 */
public interface CustomerService {

	/**
	 * Gets the customer with the given identifier.
	 * 
	 * @param customerId The id
	 * @return The customer
	 */
	Customer getCustomer(long customerId);

	/**
	 * Gets the names of all customers.
	 * 
	 * @return The list of names
	 */
	List<String> getAllCustomerNames();

	/**
	 * Gets all known customers.
	 * 
	 * @return The list of customers.
	 */
	List<Customer> getAllCustomers();

	/**
	 * Creates a new customer with the given name.
	 * 
	 * @param name The name
	 * @return The id of the created object
	 */
	long createCustomer(String name);

	/**
	 * Deletes the given customer. This is only possible, if the customer has no more projects associated.
	 * 
	 * @param customer The customer.
	 */
	void deleteCustomer(Customer customer);

	/**
	 * Saves the customer.
	 * 
	 * @param id The id
	 * @param name The name
	 */
	void saveCustomer(long id, String name);

}
