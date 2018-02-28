package com.storefront.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.MyCourse;
import com.storefront.service.IMyCourseManagement;

@RestController
@RequestMapping
public class MyCourseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCourseController.class);

	@Autowired
	IMyCourseManagement myCourseManagement;

	@GetMapping(value = "my-courses")
	public List<MyCourse> getMyCourses(OAuth2Authentication authentication) throws StoreFrontException {
		LOGGER.info("Inside the MyCourseController method");
		final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		return myCourseManagement.getAllMyCourses(details.getTokenValue());
	}

}
