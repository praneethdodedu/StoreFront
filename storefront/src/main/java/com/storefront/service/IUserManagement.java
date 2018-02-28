package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.model.User;

public interface IUserManagement {

	List<User> findAll() throws StoreFrontException;

	User fetchUserByUserId(Long userId) throws StoreFrontException;

	User saveUser(User user);

	public User getCurrentUser(String accessToken) throws StoreFrontException;
	
	public User signUpNewUser(String userName, String emailId) throws StoreFrontException;

}
