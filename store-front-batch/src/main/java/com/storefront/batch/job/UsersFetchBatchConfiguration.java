package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.storefront.batch.model.User;

/**
 * @author Praneeth.dodedu
 *
 */
@Configuration
public class UsersFetchBatchConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(UsersFetchBatchConfiguration.class);

	@Bean
	@StepScope
	ItemReader<User> userDetailsReader() {
		return new UserDataReader();
	}

	@Bean
	@StepScope
	public UserDataProcessor userDetailsProcessor() {
		return new UserDataProcessor();
	}

	@Bean
	@StepScope
	public UserDataWriter userDetailsWriter() {
		return new UserDataWriter();
	}

	@Bean
	public Step stepUsersGet() {
		logger.info("Inside method stepUsersGet() step of Batch Job");
		return stepBuilderFactory.get("stepUsersGet").<User, User>chunk(100).reader(userDetailsReader())
				.processor(userDetailsProcessor()).writer(userDetailsWriter()).build();
	}

}
