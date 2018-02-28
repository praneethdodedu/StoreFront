package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.model.User;

public interface ICourseManagement {

	public void findCoursesByCatalogId(Catalog catalog, String token) throws StoreFrontException;

	public Course findCourseByCourseId(Long courseId, User user) throws StoreFrontException;

	public List<Course> getAllCourses(String accessToken) throws StoreFrontException;

	public List<Course> findAll();

	public List<Course> searchCoursesByCourseName(String courseName);

	public List<String> getAllCourseTypes();

	public List<String> getAllCourseStates();

	public List<String> getAllCourseTags();

	public List<Course> getAllCourseByAuthor(String accessToken) throws StoreFrontException;

	public List<Course> getAllPublishedCourses(String accessToken) throws StoreFrontException;

	public Course publishCourse(Long courseId, String paymentType, Double amount, boolean isPublished,
			String trialPeriod);

	public Course disableOrEnableCoursePreview(String courseId, boolean isPreviewCourse, String moduleIds);

	public List<Course> getAllPreviewCourses() throws StoreFrontException;

	public Course enrollCourse(String courseId, String accessToken) throws StoreFrontException;

	public Course bookMarkCourse(String courseId, boolean isBookMarked, String accessToken) throws StoreFrontException;

}
