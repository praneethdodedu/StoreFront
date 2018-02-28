package com.storefront.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.storefront.batch.model.User;
import com.storefront.batch.service.IUserManagement;

public class UserDataWriter implements ItemWriter<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserDataWriter.class);

	@Autowired
	private IUserManagement iUserManagement;

	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends User> users) throws Exception {
		logger.info("This is UserDataWriter::writer method");
		iUserManagement.saveUsers((List<User>) users);
	}

}
