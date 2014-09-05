package de.tbosch.tools.timetool.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;

public class ConfigurationRepositoryTest extends AbstractSpringDbTest {

	@Autowired
	private ConfigurationRepository repository;

	@Before
	public void before() throws Exception {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/ConfigurationDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Configuration config = repository.findOne(1L);
		assertNotNull(config);
	}

	@Test
	public void findValue() {
		String value = repository.findByKey(Key.LAST_USED_PASS).getValue();
		assertNotNull(value);
		assertEquals("value1", value);

		Configuration config = repository.findByKey(Key.LAST_USED_TICKET);
		assertNull(config);
	}

	@Test
	public void findByKey() {
		Configuration configuration = repository.findByKey(Key.LAST_USED_PASS);
		assertNotNull(configuration);
		assertEquals("value1", configuration.getValue());
	}

}
