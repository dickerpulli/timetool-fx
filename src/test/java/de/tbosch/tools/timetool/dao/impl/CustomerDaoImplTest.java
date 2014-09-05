package de.tbosch.tools.timetool.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.dao.CustomerDao;
import de.tbosch.tools.timetool.model.Customer;

public class CustomerDaoImplTest extends AbstractSpringDbTest {

	@Autowired
	private CustomerDao customerDao;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/CustomerDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Customer customer = customerDao.read(1L);
		assertNotNull(customer);
	}

}
