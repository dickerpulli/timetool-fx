package de.tbosch.tools.timetool.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic interface to implement the main CRUD functions in a DAO.
 * 
 * @author Thomas Bosch
 * 
 * @param <T> The type of the object
 * @param <K> The type of the identifier
 */
public interface GenericDao<T, K extends Serializable> {

	/**
	 * Persists a transient object.
	 * 
	 * @param transientEntity The object to create
	 * @return The new identifier
	 */
	K create(T transientEntity);

	/**
	 * Reads an object.
	 * 
	 * @param id The identifier
	 * @return The detachted object
	 */
	T read(K id);

	/**
	 * Deletes a given detached object.
	 * 
	 * @param persistentEntity The object to delete
	 */
	void delete(T persistentEntity);

	/**
	 * Update values of a detached object.
	 * 
	 * @param persistentEntity The object to update
	 * @return The updated object
	 */
	T update(T persistentEntity);

	/**
	 * Find all entities.
	 * 
	 * @return all
	 */
	List<T> findAll();

	/**
	 * Flushes all entities managed by this Dao.
	 */
	void flush();

	/**
	 * Removes all entities from the second level cache.
	 */
	void evict();

	/**
	 * Removes entity from the second level cache.
	 * 
	 * @param entity The object to remove
	 */
	void evict(Object entity);

}
