package de.tbosch.tools.timetool.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.dao.ConfigurationDao;
import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;

public class ConfigurationDaoImplTest extends AbstractSpringDbTest {

	@Autowired
	private ConfigurationDao configurationDao;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/ConfigurationDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Configuration config = configurationDao.read(1L);
		assertNotNull(config);
	}

	@Test
	public void findValue() {
		String value = configurationDao.findValue(Key.LAST_USED_PASS);
		assertNotNull(value);
		assertEquals("value1", value);

		value = configurationDao.findValue(Key.LAST_USED_TICKET);
		assertNull(value);
	}

	@Test
	public void findByKey() {
		Configuration configuration = configurationDao.findByKey(Key.LAST_USED_PASS);
		assertNotNull(configuration);
		assertEquals("value1", configuration.getValue());
	}

}
