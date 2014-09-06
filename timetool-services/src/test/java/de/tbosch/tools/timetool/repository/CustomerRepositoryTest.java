package de.tbosch.tools.timetool.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringDbJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;
import de.tbosch.tools.timetool.model.Customer;

@ContextConfiguration(classes = TestConfiguration.class)
public class CustomerRepositoryTest extends AbstractSpringDbJavaConfigTest {

	@Autowired
	private CustomerRepository repository;

	@Before
	public void before() throws Exception {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/CustomerDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Customer customer = repository.findOne(1L);
		assertNotNull(customer);
	}

}
