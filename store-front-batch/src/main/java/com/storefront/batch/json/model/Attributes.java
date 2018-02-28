package com.storefront.batch.json.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class Attributes {

	private String name;
	private String state;
	private String email;
	private String description;
	private String courseType;
	private String datePublished;
	private String effectivenessIndex;
	private String enrollmentType;
	private String moduleOrderEnforced;
	private String overview;
	private String avatarUrl;
	private String profile;
	private String userType;
	private String moduleType;
	private String title;
	private String bio;
	private String courseOrderEnforced;
	private String dateCreated;
	private String dateUpdated;
	private String lastLoginDate;
	private String dateEnrolled;
	private String dateStarted;
	private String dateCompleted;

	@JsonProperty("isDefault")
	private boolean isDefault;
	private boolean passed;
	
	private Integer pointsEarned;
	private Integer progressPercent;
	
	private List<String> roles;
	private List<String> tags;
	private List<String> authorNames;
	
}
