package de.tbosch.tools.timetool.dao;

import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;

/**
 * DAO interface to access {@link Configuration} objects.
 * 
 * @author Thomas Bosch
 */
public interface ConfigurationDao extends GenericDao<Configuration, Long> {

	/**
	 * Searches for the configuration value by the given key.
	 * 
	 * @param key The configuration key
	 * @return The value
	 */
	String findValue(Key key);

	/**
	 * Gives the configuration entry of the unique key.
	 * 
	 * @param key The key
	 * @return The configuration entry
	 */
	Configuration findByKey(Key key);

}
