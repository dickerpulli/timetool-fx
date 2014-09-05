package de.tbosch.tools.timetool.service;

import java.util.List;

import de.tbosch.tools.timetool.model.Configuration.Key;

/**
 * Service for accessing configuration entities.
 * 
 * @author Thomas Bosch
 */
public interface ConfigurationService {

	/**
	 * Gets the value of the configuration.
	 * 
	 * @param key The key of this value
	 * @return The value
	 */
	String getValue(Key key);

	/**
	 * Sets the value of the given key.
	 * 
	 * @param key The key
	 * @param value The value
	 */
	void setValue(Key key, String value);

	/**
	 * Gets the values of the configuration as list.
	 * 
	 * @param key The key of this value
	 * @return The values
	 */
	List<String> getValueList(Key key);

	/**
	 * Sets the values of the given key as list.
	 * 
	 * @param key The key
	 * @param values The values
	 */
	void setValueList(Key key, List<String> values);

	/**
	 * Adds the value to the list of the given key. If no list exists it creates a new list. If the element is already in the list, the operation will be
	 * canceled.
	 * 
	 * @param key The key
	 * @param value The value
	 */
	void addValueToList(Key key, String value);

}
