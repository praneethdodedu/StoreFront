package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.MyCourse;

public interface IMyCourseManagement {

	public List<MyCourse> getAllMyCourses(String accessToken) throws StoreFrontException;

}
