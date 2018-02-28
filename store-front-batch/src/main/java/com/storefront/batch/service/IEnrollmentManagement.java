package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.Enrollment;
import com.storefront.batch.model.User;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IEnrollmentManagement {

	public List<Enrollment> getEnrollments(User user, String token) throws StoreFrontException;
	
	public void saveEnrollments(List<Enrollment> enrollments) throws StoreFrontException;
	
	List<Enrollment> getAllEnrollments(String accessToken);

}
