package com.storefront.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "module")
public @Data class Module {

	@Id
	private long moduleId;
	private String type;
	@Column(length = 1000)
	private String description;
	private String moduleType;
	@NotBlank
	private String title;
	private Date dateUpdated;
	
	private boolean isPreview = Boolean.FALSE;

	public Module() {
		// TODO Auto-generated constructor stub
	}

	public Module(long moduleId, String type, String description, String moduleType, String title, Date dateUpdated) {
		super();
		this.moduleId = moduleId;
		this.type = type;
		this.description = description;
		this.moduleType = moduleType;
		this.title = title;
		this.dateUpdated = dateUpdated;
	}

}
