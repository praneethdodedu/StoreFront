package com.storefront.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.storefront.common.StoreFrontException;
import com.storefront.model.User;

@FeignClient("user-service")
public interface IUserManagement {

	@RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
	User fetchUserByUserId(@PathVariable("userId") Long userId) throws StoreFrontException;

	@RequestMapping(method = RequestMethod.GET, value = "/users/getCurrentUser/{accessToken}")
	public User getCurrentUser(@PathVariable("accessToken") String accessToken) throws StoreFrontException;

}
