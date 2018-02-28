package com.storefront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storefront.model.Course;
import com.storefront.model.Enrollment;
import com.storefront.model.User;
import com.storefront.repository.IEnrollmentRepository;
import com.storefront.service.IEnrollmentManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class EnrollmentManagementImpl implements IEnrollmentManagement {

	@Autowired
	private IEnrollmentRepository iEnrollmentRepository;

	@Override
	public List<Enrollment> findAllByUserId(User user) {
		return iEnrollmentRepository.findAllByUserId(user);
	}
	
	@Override
	public Enrollment findByUserAndCourse(User user, Course course) {
		return iEnrollmentRepository.findByUserAndCourse(user, course);
	}
}
