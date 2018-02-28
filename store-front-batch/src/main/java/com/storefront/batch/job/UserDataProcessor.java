package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.storefront.batch.model.User;

public class UserDataProcessor implements ItemProcessor<User, User> {

	private static final Logger log = LoggerFactory.getLogger(UserDataProcessor.class);

	@Override
	public User process(final User user) throws Exception {
		log.info("Inside UserDataProcessor:: process method");
		return user;
	}

}
