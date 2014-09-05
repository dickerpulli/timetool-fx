package de.tbosch.tools.timetool.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * The project entity.
 * 
 * @author Thomas Bosch
 */
@Entity
public class Project {

	@Id
	@GeneratedValue
	private long id;

	@NotEmpty
	@Length(max = 32)
	@Column(unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
	private Set<Timeslot> timeslots = new HashSet<Timeslot>();

	private boolean active;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the timeslots
	 */
	public Set<Timeslot> getTimeslots() {
		return timeslots;
	}

	/**
	 * @param timeslots the timeslots to set
	 */
	public void setTimeslots(Set<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
