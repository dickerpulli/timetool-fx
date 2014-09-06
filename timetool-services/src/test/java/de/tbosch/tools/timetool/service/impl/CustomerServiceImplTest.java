package de.tbosch.tools.timetool.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringDbJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;
import de.tbosch.tools.timetool.service.CustomerService;

@ContextConfiguration(classes = TestConfiguration.class)
public class CustomerServiceImplTest extends AbstractSpringDbJavaConfigTest {

	@Autowired
	private CustomerService customerService;

	@Before
	public void before() throws Exception {
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
