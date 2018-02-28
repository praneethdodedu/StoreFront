package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.storefront.batch.model.Course;

public class CourseDataProcessor implements ItemProcessor<Course, Course> {

	private static final Logger log = LoggerFactory.getLogger(UserDataProcessor.class);

	@Override
	public Course process(final Course course) throws Exception {
		log.info("Inside CourseDataProcessor:: process method");
		return course;
	}

}
