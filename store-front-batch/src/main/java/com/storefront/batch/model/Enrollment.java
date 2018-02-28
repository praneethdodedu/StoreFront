package com.storefront.batch.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "enrollment")
public @Data class Enrollment implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6996076215899211961L;

	@Id
	private String enrollmentId;
	private String type;
	private boolean isPassed;
	private String pointEarned;
	private String progressCount;
	private Date dateEnrolled;
	private Date dateStarted;
	private Date dateCompleted;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Enrollment() {

	}

	public Enrollment(String enrollmentId, String type, boolean isPassed, String pointEarned, String progressCount,
			Date dateEnrolled, Date dateStarted, Date dateCompleted) {
		super();
		this.enrollmentId = enrollmentId;
		this.type = type;
		this.isPassed = isPassed;
		this.pointEarned = pointEarned;
		this.progressCount = progressCount;
		this.dateEnrolled = dateEnrolled;
		this.dateStarted = dateStarted;
		this.dateCompleted = dateCompleted;
	}

}
