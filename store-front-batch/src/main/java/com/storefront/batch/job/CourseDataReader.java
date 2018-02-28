package com.storefront.batch.job;

import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_ACCESS_TOKEN;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.storefront.batch.model.Course;
import com.storefront.batch.service.ICourseManagement;

public class CourseDataReader implements ItemReader<Course> {

	private static final Logger logger = LoggerFactory.getLogger(CourseDataReader.class);

	@Autowired
	private ICourseManagement iCourseManagement;

	private int nextCourseIndex;
	private List<Course> courses;
	@Value("#{jobParameters[offsetId]}")
	public Long offsetId;
	public int limit = 10;

	@Value(PROPERTY_KEY_ACP_ACCESS_TOKEN)
	private String accessToken;

	public CourseDataReader() {
		nextCourseIndex = 0;
	}

	@Override
	public Course read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("Inside CourseDataReader::read() method");
		if (isCourseListNotInitialized())
			courses = iCourseManagement.getAllCourses(accessToken, offsetId);
		Course nextCourse = null;
		if (nextCourseIndex < courses.size()) {
			nextCourse = courses.get(nextCourseIndex);
			nextCourseIndex++;
		}
		if (nextCourseIndex == courses.size()) {
			offsetId = offsetId + limit;
			nextCourseIndex = 0;
			courses = null;
		}
		return nextCourse;
	}

	private boolean isCourseListNotInitialized() {
		return this.courses == null;
	}
}
