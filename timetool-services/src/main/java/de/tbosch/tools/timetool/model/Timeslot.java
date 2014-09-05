package de.tbosch.tools.timetool.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;

import de.tbosch.tools.timetool.utils.DateUtils;
import de.tbosch.tools.timetool.utils.context.MessageHelper;

/**
 * The timeslot entity.
 * 
 * @author Thomas Bosch
 */
@Entity
public class Timeslot implements Comparable<Timeslot> {

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date starttime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date endtime;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Project project;

	private boolean active;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean marked;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (endtime == null) {
			return DateUtils.toTimeString(starttime) + " - " + MessageHelper.getMessage("timeslot.open");
		} else {
			String diff = DateUtils.getDifferenceAsString(starttime, endtime, true, true);
			return DateUtils.toTimeString(starttime) + " - " + DateUtils.toTimeString(endtime) + " = " + diff;
		}
	}

	/**
	 * Resturn String representation on this timeslot with the day included.
	 * 
	 * @return The formatted text
	 */
	public String toStringWithDate() {
		if (endtime == null) {
			return DateUtils.toDateTimeString(starttime) + " - " + MessageHelper.getMessage("timeslot.open");
		} else {
			String diff = DateUtils.getDifferenceAsString(starttime, endtime, true, true);
			return DateUtils.toDateTimeString(starttime) + " - " + DateUtils.toDateTimeString(endtime) + " = " + diff;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Timeslot o) {
		return this.getStarttime().compareTo(o.getStarttime());
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
	 * @return the starttime
	 */
	public Date getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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

	/**
	 * @return the marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

}
