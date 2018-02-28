package com.storefront.batch.model;

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

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "catalog")
public @Data class Catalog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String catalogId;
	@NotBlank
	private String catalogName;
	private String state;
	private String description;
	private boolean isDefault;
	private Date dateCreated;
	private Date dateUpdated;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "catalog_course", joinColumns = {
			@JoinColumn(name = "catalog_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "course_id", nullable = false, updatable = false) })
	public List<Course> courses = new ArrayList<Course>(0);

	public Catalog() {
	}

	public Catalog(String catalogId, String catalogName, String state, String description, boolean isDefault,
			Date dateCreated, Date dateUpdated) {
		super();
		this.catalogId = catalogId;
		this.catalogName = catalogName;
		this.state = state;
		this.description = description;
		this.isDefault = isDefault;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
	}

}
