package com.storefront.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "learning_program")
public @Data class LearningProgram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7783374347421711197L;

	@Id
	private Long learningProgramId;
	private String learningProgramName;
	private String state;
	private String type;
	private String courseOrderEnforced;
	private Date dateCreated;
	private Date dateUpdated;
	private Long effectivenessIndex;
	private String enrollmentType;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "learningprogram_course", joinColumns = {
			@JoinColumn(name = "learning_program_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "course_id", nullable = false, updatable = false) })
	private List<Course> courses = new ArrayList<Course>(0);

	public LearningProgram() {
	}

	public LearningProgram(Long learningProgramId, String learningProgramName, String state, String type,
			String courseOrderEnforced, Date dateCreated, Date dateUpdated, Long effectivenessIndex,
			String enrollmentType) {
		super();
		this.learningProgramId = learningProgramId;
		this.learningProgramName = learningProgramName;
		this.state = state;
		this.type = type;
		this.courseOrderEnforced = courseOrderEnforced;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.effectivenessIndex = effectivenessIndex;
		this.enrollmentType = enrollmentType;
	}

}
