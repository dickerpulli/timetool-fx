package de.tbosch.tools.timetool.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;
import de.tbosch.tools.timetool.repository.ConfigurationRepository;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

	/** Delimiter for the list value. */
	private static final String DELIMITER = "@#;#@";

	@Autowired
	private ConfigurationRepository configurationRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(Key key) {
		Configuration configuration = configurationRepository.findByKey(key);
		if (configuration == null || configuration.getValue() == null) {
			LogUtils.logWarn("Configuration for key " + key + " not found", LOGGER);
			return null;
		}
		return configuration.getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Key key, String value) {
		Validate.notNull(key, "the key should not be null");
		Validate.notNull(value, "the value should not be null");
		Configuration configuration = configurationRepository.findByKey(key);
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setKey(key);
			configuration.setValue(value);
			configurationRepository.save(configuration);
			LogUtils.logInfo("New configuration for key " + key + " was created: " + value, LOGGER);
		} else {
			configuration.setValue(value);
			configurationRepository.save(configuration);
			LogUtils.logDebug("New configuration for key " + key + " is now: " + value, LOGGER);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getValueList(Key key) {
		Configuration configuration = configurationRepository.findByKey(key);
		if (configuration == null) {
			LogUtils.logWarn("Configuration for key " + key + " not found", LOGGER);
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
		Configuration configuration = configurationRepository.findByKey(key);
		String valuesString = "";
		for (String value : values) {
			valuesString += value + DELIMITER;
		}
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setKey(key);
			configuration.setValue(valuesString);
			configurationRepository.save(configuration);
			LogUtils.logInfo("New configuration for key " + key + " was created: " + valuesString, LOGGER);
		} else {
			configuration.setValue(valuesString);
			configurationRepository.save(configuration);
			LogUtils.logDebug("New configuration for key " + key + " is now: " + valuesString, LOGGER);
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
			LogUtils.logDebug("Adding element to non existing list, create new list for key " + key, LOGGER);
		}
		// Add the new value to the old list and save it
		// Only insert new value if it not already exists in the list
		if (!values.contains(value)) {
			values.add(value);
			setValueList(key, values);
		}
	}

}
