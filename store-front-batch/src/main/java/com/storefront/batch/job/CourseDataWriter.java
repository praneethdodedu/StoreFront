package com.storefront.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.storefront.batch.model.Course;
import com.storefront.batch.service.ICourseManagement;

public class CourseDataWriter implements ItemWriter<Course> {

	private static final Logger logger = LoggerFactory.getLogger(UserDataWriter.class);

	@Autowired
	private ICourseManagement iCourseManagement;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Course> courses) throws Exception {
		logger.info("This is CourseDataWriter::writer method");
		iCourseManagement.saveCourses((List<Course>) courses);
	}

}
