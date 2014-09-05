package de.tbosch.tools.timetool.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.dao.ConfigurationDao;
import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.service.ConfigurationService;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * Standard implementation of the ConfigurationService.
 * 
 * @author Thomas Bosch
 */
@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

	/** The logger. */
	private static final Log LOG = LogFactory.getLog(ConfigurationServiceImpl.class);

	/** Delimiter for the list value. */
	private static final String DELIMITER = "@#;#@";

	@Autowired
	private ConfigurationDao configurationDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(Key key) {
		String value = configurationDao.findValue(key);
		if (value == null) {
			LogUtils.logWarn("Configuration for key " + key + " not found", LOG);
			return null;
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Key key, String value) {
		Validate.notNull(key, "the key should not be null");
		Validate.notNull(value, "the value should not be null");
		Configuration configuration = configurationDao.findByKey(key);
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setKey(key);
			configuration.setValue(value);
			configurationDao.create(configuration);
			LogUtils.logInfo("New configuration for key " + key + " was created: " + value, LOG);
		} else {
			configuration.setValue(value);
			configurationDao.update(configuration);
			LogUtils.logDebug("New configuration for key " + key + " is now: " + value, LOG);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getValueList(Key key) {
		Configuration configuration = configurationDao.findByKey(key);
		if (configuration == null) {
			LogUtils.logWarn("Configuration for key " + key + " not found", LOG);
			return null;
		}
		String valuesString = configuration.getValue();
		String[] valuesArray = valuesString.split(DELIMITER);
		List<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValueList(Key key, List<String> values) {
		Validate.notNull(key, "the key should not be null");
		Validate.notNull(values, "the value list should not be null");
		Configuration configuration = configurationDao.findByKey(key);
		String valuesString = "";
		for (String value : values) {
			valuesString += value + DELIMITER;
		}
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setKey(key);
			configuration.setValue(valuesString);
			configurationDao.create(configuration);
			LogUtils.logInfo("New configuration for key " + key + " was created: " + valuesString, LOG);
		} else {
			configuration.setValue(valuesString);
			configurationDao.update(configuration);
			LogUtils.logDebug("New configuration for key " + key + " is now: " + valuesString, LOG);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addValueToList(Key key, String value) {
		Validate.notNull(key, "the key should not be null");
		Validate.notNull(value, "the value should not be null");
		// First get the last configuration
		List<String> values = getValueList(key);
		if (values == null) {
			values = new ArrayList<String>();
			LogUtils.logDebug("Adding element to non existing list, create new list for key " + key, LOG);
		}
		// Add the new value to the old list and save it
		// Only insert new value if it not already exists in the list
		if (!values.contains(value)) {
			values.add(value);
			setValueList(key, values);
		}
	}

}
