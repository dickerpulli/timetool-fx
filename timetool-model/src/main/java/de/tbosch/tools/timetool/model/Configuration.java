package de.tbosch.tools.timetool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Configuration holder.
 * 
 * @author Thomas Bosch
 */
@Entity
public class Configuration {

	/**
	 * The configuration keys.
	 */
	public enum Key {
		LAST_USED_URL(false), LAST_USED_USER(false), LAST_USED_PASS(false), LAST_USED_TICKET(false), LAST_USED_PASS_SAVE(false), LAST_USED_TICKET_LIST(true),
		LAST_USED_STARTROW(false), LAST_USED_DATECOLUMN(false), LAST_USED_STARTCOLUMN(false), LAST_USED_ENDCOLUMN(false), LAST_USED_PAUSECOLUMN(false),
		LAST_USED_INPUT_FILE(false), LAST_USED_OUTPUT_FOLDER(false);

		private boolean listKey;

		/**
		 * Private constructor for this enum.
		 * 
		 * @param listKey The marker for list keys
		 */
		private Key(boolean listKey) {
			this.listKey = listKey;
		}

		/**
		 * Shows if this key is a special key for a value list.
		 * 
		 * @return the listKey
		 */
		public boolean isListKey() {
			return listKey;
		}

	}

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	private Key key;

	@NotNull
	@Length(max = 128)
	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return key + ":" + value;
	}

	// Getter / Setter

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
