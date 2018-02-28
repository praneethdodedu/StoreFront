package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@Import({ BatchScheduler.class })
public class DataFetchJobConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataFetchJobConfiguration.class);

	@Autowired
	private UsersFetchBatchConfiguration usersFetchBatchConfiguration;

	@Autowired
	private CatalogsFetchBatchConfiguration catalogsFetchBatchConfiguration;

	@Autowired
	private CoursesFetchBatchConfiguration coursesFetchBatchConfiguration;

	@Autowired
	private LearningProgramFetchBatchConfiguration learningProgramFetchBatchConfiguration;

	@Autowired
	private EnrollmentFetchBatchConfiguration enrollmentFetchBatchConfiguration;

	// @Scheduled(cron = "0 0/15 * * * ?")
	@Scheduled(fixedRate = 5000000)
	public void runDataFetchBatchJob() {
		logger.info("Running all the jobs inside DataFetchJobConfiguration::runDataFetchBatchJob() method");
		super.runBatchJob("USERS_FETCH_JOB", usersFetchBatchConfiguration.stepUsersGet());
		super.runBatchJob("CATALOGS_FETCH_JOB", catalogsFetchBatchConfiguration.stepCatalogsGet());
		super.runBatchJob("COURSES_FETCH_JOB", coursesFetchBatchConfiguration.stepCoursesGet());
		super.runBatchJob("LEARNING_PROGRAMS_FETCH_JOB",
				learningProgramFetchBatchConfiguration.stepLearningProgramsGet());
		super.runBatchJob("ENROLLMENT_FETCH_JOB", enrollmentFetchBatchConfiguration.stepEnrollmentsGet());
	}

}
