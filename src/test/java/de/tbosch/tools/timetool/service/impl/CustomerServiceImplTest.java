package de.tbosch.tools.timetool.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.service.CustomerService;

@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceImplTest extends AbstractSpringDbTest {

	@Autowired
	private CustomerService customerService;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/service/CustomerServiceImplTest.sql");
	}

	@Test
	public void testGetAllCustomerNames() {
		List<String> customerNames = customerService.getAllCustomerNames();
		assertEquals(1, customerNames.size());
		assertEquals("customer1", customerNames.get(0));
	}

}
