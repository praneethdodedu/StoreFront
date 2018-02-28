package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.Catalog;
import com.storefront.batch.model.Course;

public interface ICourseManagement {

	public void findCoursesByCatalogId(Catalog catalog, String token) throws StoreFrontException;

	public List<Course> getAllCourses(String token, Long offsetId) throws StoreFrontException;

	public void saveCourses(List<Course> courses) throws StoreFrontException;

}
