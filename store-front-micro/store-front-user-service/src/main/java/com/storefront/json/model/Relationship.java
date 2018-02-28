package com.storefront.json.model;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
public @Data class Relationship {

	private CourseModule courseModules;

	private CourseSkill courseSkills;

	private Instance instances;

	private Author authors;

	private Manager manager;

	private ModuleVersion moduleVersions;

	private Account account;

	private CourseModel course;

	private CourseInstance courseInstance;

	private Learner learner;

	private CourseModels courses;

}
