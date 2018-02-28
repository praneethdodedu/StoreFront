package com.storefront.batch.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "course_publish")
public @Data class CoursePublish implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3921503987093396994L;

	@Id
	private Long coursePublishId;

	private Boolean isPublished;

	private String paymentType;

	@NumberFormat
	private Double amount;

	@NumberFormat
	private String trialPeriod;
	
	private Boolean isPreview;

}
