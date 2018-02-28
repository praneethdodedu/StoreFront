package com.storefront.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "course")
public @Data class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long courseId;
	@NotBlank
	private String courseName;
	private String courseDescription;
	@Column(length = 1500)
	private String courseOverview;
	private String courseState;
	@NotBlank
	private String coursetype;
	private String enrollmentType;
	private Date dateCreated;
	private Date dateUpdated;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "course_module", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = {
			@JoinColumn(name = "module_id") })
	private List<Module> module;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@ElementCollection
	@CollectionTable(name = "Tags", joinColumns = @JoinColumn(name = "course_id"))
	@Column(name = "tag_name")
	private List<String> tags;

	private String courseInstance;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "course_publish_id")
	private CoursePublish coursePublish;

	@Transient
	private boolean isEnrolled;

	@Transient
	private String progressCount;

	@Transient
	private boolean isBookMarked;

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public Course(Long courseId, String courseName, String courseDescription, String courseOverview, String courseState,
			String coursetype, String enrollmentType, Date dateCreated, Date dateUpdated, Boolean isPublished,
			List<String> tags) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.courseOverview = courseOverview;
		this.courseState = courseState;
		this.coursetype = coursetype;
		this.enrollmentType = enrollmentType;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.tags = tags;
	}

}
