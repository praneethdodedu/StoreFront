package com.storefront.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "user_course_operation")
public @Data class UserCourseOperation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6654193316430742820L;

	@Id
	private String userCourseId;

	private boolean isBookMarked;

}
