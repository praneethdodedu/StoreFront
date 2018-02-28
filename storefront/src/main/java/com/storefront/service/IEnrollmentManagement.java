package com.storefront.service;

import java.util.List;

import com.storefront.model.Course;
import com.storefront.model.Enrollment;
import com.storefront.model.User;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IEnrollmentManagement {

	public List<Enrollment> findAllByUserId(User user);

	public Enrollment findByUserAndCourse(User user, Course course);

}
