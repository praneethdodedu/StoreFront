package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.storefront.batch.model.LearningProgram;

/**
 * @author Praneeth.dodedu
 *
 */
@Configuration
public class LearningProgramFetchBatchConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(LearningProgramFetchBatchConfiguration.class);

	@Bean
	@StepScope
	ItemReader<LearningProgram> learningProgramDetailsReader() {
		return new LearningProgramReader();
	}

	@Bean
	@StepScope
	public LearningProgramProcessor learningProgramDetailsProcessor() {
		return new LearningProgramProcessor();
	}

	@Bean
	@StepScope
	public LearningProgramWriter learningProgramDetailsWriter() {
		return new LearningProgramWriter();
	}

	@Bean
	public Step stepLearningProgramsGet() {
		logger.info("Inside method stepLearningProgramsGet() step of Batch Job");
		return stepBuilderFactory.get("stepLearningProgramsGet").<LearningProgram, LearningProgram>chunk(100)
				.reader(learningProgramDetailsReader()).processor(learningProgramDetailsProcessor())
				.writer(learningProgramDetailsWriter()).build();
	}

}
