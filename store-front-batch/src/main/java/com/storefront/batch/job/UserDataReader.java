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

import com.storefront.batch.model.User;
import com.storefront.batch.service.IUserManagement;

public class UserDataReader implements ItemReader<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserDataReader.class);

	@Autowired
	private IUserManagement iUserManagement;

	private int nextUserIndex;
	public List<User> users;
	@Value("#{jobParameters[offsetId]}")
	public Long offsetId;
	public int limit = 10;

	@Value(PROPERTY_KEY_ACP_ACCESS_TOKEN)
	private String accessToken;

	public UserDataReader() {
		nextUserIndex = 0;
	}

	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException {
		logger.info("Inside UserDataReader::read() method");
		if (isUserListNotInstantiated()) {
			users = iUserManagement.getAllUsers(accessToken, offsetId);
		}
		User nextUser = null;
		if (nextUserIndex < users.size()) {
			nextUser = users.get(nextUserIndex);
			nextUserIndex++;
		}
		if (nextUserIndex == limit) {
			offsetId = offsetId + limit;
			nextUserIndex = 0;
			users = null;
		}
		return nextUser;
	}

	public boolean isUserListNotInstantiated() {
		return this.users == null;
	}

}
