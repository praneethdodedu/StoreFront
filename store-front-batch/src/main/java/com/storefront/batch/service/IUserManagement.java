package com.storefront.batch.service;

import java.util.List;

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.model.User;

public interface IUserManagement {

	List<User> getAllUsers(String token, Long offsetId) throws StoreFrontException;

	public void saveUsers(List<User> users) throws StoreFrontException;

	User fetchUserByUserId(Long userId) throws StoreFrontException;

}
