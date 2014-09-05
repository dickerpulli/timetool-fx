package de.tbosch.tools.timetool.gui.model;

/**
 * A Ticket is a container for Jira tickets. It holds some attributes of the ticket, like name or summary.
 * 
 * @author Thomas Bosch
 */
public class Ticket {

	private String name;

	private String summary;

	/**
	 * Constructor.
	 * 
	 * @param name The name
	 * @param summary The summary
	 */
	public Ticket(String name, String summary) {
		super();
		this.name = name;
		this.summary = summary;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name + " (" + summary + ")";
	}

	// Getter / Setter

	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ticket other = (Ticket) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (summary == null) {
			if (other.summary != null) {
				return false;
			}
		} else if (!summary.equals(other.summary)) {
			return false;
		}
		return true;
	}

}
