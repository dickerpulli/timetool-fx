package de.tbosch.tools.timetool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.service.ConfigurationService;

@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ConfigurationServiceImplTest extends AbstractSpringDbTest {

	@Autowired
	private ConfigurationService configurationService;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/service/ConfigurationServiceImplTest.sql");
	}

	@Test
	public void testSetValueNew() {
		String value = configurationService.getValue(Key.LAST_USED_USER);
		assertNull(value);

		configurationService.setValue(Key.LAST_USED_USER, "value1");

		value = configurationService.getValue(Key.LAST_USED_USER);
		assertEquals("value1", value);
	}

	@Test
	public void testSetValueUpdate() {
		String value = configurationService.getValue(Key.LAST_USED_PASS);
		assertEquals("value1", value);

		configurationService.setValue(Key.LAST_USED_PASS, "value2");

		value = configurationService.getValue(Key.LAST_USED_PASS);
		assertEquals("value2", value);
	}

	@Test
	public void testGetValue() {
		String value = configurationService.getValue(Key.LAST_USED_PASS);
		assertEquals("value1", value);

		value = configurationService.getValue(Key.LAST_USED_PASS_SAVE);
		assertNull(value);
	}

	@Test
	public void testGetValueList() {
		List<String> values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(3, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));
	}

	@Test
	public void testSetValueListNew() {
		List<String> values = configurationService.getValueList(Key.LAST_USED_URL);
		assertNull(values);

		List<String> valuesNew = Arrays.asList("value1", "value2");
		configurationService.setValueList(Key.LAST_USED_URL, valuesNew);

		values = configurationService.getValueList(Key.LAST_USED_URL);
		assertEquals(2, values.size());
		assertEquals("value1", values.get(0));
		assertEquals("value2", values.get(1));
	}

	@Test
	public void testSetValueListUpdate() {
		List<String> values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(3, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));

		List<String> valuesNew = Arrays.asList("value1", "value2");
		configurationService.setValueList(Key.LAST_USED_TICKET_LIST, valuesNew);

		values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(2, values.size());
		assertEquals("value1", values.get(0));
		assertEquals("value2", values.get(1));
	}

	@Test
	public void testAddValueToList() {
		List<String> values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(3, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));

		configurationService.addValueToList(Key.LAST_USED_TICKET_LIST, "value5");

		values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(4, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));
		assertEquals("value5", values.get(3));
	}

	@Test
	public void testAddValueToListNoListExists() {
		configurationService.addValueToList(Key.LAST_USED_URL, "value1");

		List<String> values = configurationService.getValueList(Key.LAST_USED_URL);
		assertEquals(1, values.size());
		assertEquals("value1", values.get(0));
	}

	@Test
	public void testAddValueToListAlreadyExists() {
		List<String> values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(3, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));

		configurationService.addValueToList(Key.LAST_USED_TICKET_LIST, "value3");

		values = configurationService.getValueList(Key.LAST_USED_TICKET_LIST);
		assertEquals(3, values.size());
		assertEquals("value1,value2", values.get(0));
		assertEquals("value3", values.get(1));
		assertEquals("value4", values.get(2));
	}

}
