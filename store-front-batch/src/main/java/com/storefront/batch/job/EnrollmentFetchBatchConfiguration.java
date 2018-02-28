package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.storefront.batch.model.Enrollment;

/**
 * @author Praneeth.dodedu
 *
 */
@Configuration
public class EnrollmentFetchBatchConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(EnrollmentFetchBatchConfiguration.class);

	@Bean
	@StepScope
	ItemReader<Enrollment> enrollmentDetailsReader() {
		return new EnrollmentDataReader();
	}

	@Bean
	@StepScope
	public EnrollmentDataProcessor enrollmentDetailsProcessor() {
		return new EnrollmentDataProcessor();
	}

	@Bean
	@StepScope
	public EnrollmentDataWriter enrollmentDetailsWriter() {
		return new EnrollmentDataWriter();
	}

	@Bean
	public Step stepEnrollmentsGet() {
		logger.info("Inside method stepEnrollmentsGet() step of Batch Job");
		return stepBuilderFactory.get("stepEnrollmentsGet").<Enrollment, Enrollment>chunk(100)
				.reader(enrollmentDetailsReader()).processor(enrollmentDetailsProcessor())
				.writer(enrollmentDetailsWriter()).build();
	}

}
