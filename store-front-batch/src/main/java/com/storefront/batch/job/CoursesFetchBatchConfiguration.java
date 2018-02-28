package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.storefront.batch.model.Course;

@Configuration
public class CoursesFetchBatchConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(CoursesFetchBatchConfiguration.class);

	@Bean
	@StepScope
	ItemReader<Course> courseDetailsReader() {
		return new CourseDataReader();
	}

	@Bean
	@StepScope
	public CourseDataProcessor courseDetailsProcessor() {
		return new CourseDataProcessor();
	}

	@Bean
	@StepScope
	public CourseDataWriter courseDetailsWriter() {
		return new CourseDataWriter();
	}

	@Bean
	public Step stepCoursesGet() {
		logger.info("Inside method stepUsersGet() step of Batch Job");
		return stepBuilderFactory.get("stepCoursesGet").<Course, Course>chunk(100).reader(courseDetailsReader())
				.processor(courseDetailsProcessor()).writer(courseDetailsWriter()).build();
	}

}
