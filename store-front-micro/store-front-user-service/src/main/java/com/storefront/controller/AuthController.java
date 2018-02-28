package com.storefront.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storefront.common.StoreFrontException;

@RestController
@RequestMapping(value = "/authenticate")
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@GetMapping(value = "/isLoggedIn")
	public HttpStatus isLoggedIn() throws StoreFrontException {
		LOGGER.info("Inside index method of Auth controller");
		return null;
	}

}
