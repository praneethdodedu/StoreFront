package com.storefront.service;

import java.util.List;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.model.Course;

public interface ICourseManagement {

	public void findCoursesByCatalogId(Catalog catalog, String token) throws StoreFrontException;

	public Course findCourseByCourseId(Long courseId, String accessToken) throws StoreFrontException;

	public List<Course> getAllCourses(String accessToken) throws StoreFrontException;

	public List<Course> findAll();

	public List<Course> searchCoursesByCourseName(String courseName);

	public List<String> getAllCourseTypes();

	public List<String> getAllCourseStates();

	public List<String> getAllCourseTags();

	public List<Course> getAllCourseByAuthor(OAuth2Authentication authentication) throws StoreFrontException;

	public List<Course> getAllPublishedCourses(OAuth2Authentication authentication) throws StoreFrontException;

	public Course publishCourse(Long courseId, String paymentType, Double amount, boolean isPublished,
			String trialPeriod);

	public Course disableOrEnableCoursePreview(String courseId, boolean isPreviewCourse, String moduleIds);

	public List<Course> getAllPreviewCourses() throws StoreFrontException;

	public Course enrollCourse(String courseId, OAuth2Authentication authentication) throws StoreFrontException;

	public Course bookMarkCourse(String courseId, boolean isBookMarked, OAuth2Authentication authentication)
			throws StoreFrontException;

}
