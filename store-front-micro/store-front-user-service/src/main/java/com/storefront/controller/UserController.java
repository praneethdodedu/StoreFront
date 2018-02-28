package com.storefront.controller;

import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_SUCCESS_USER_REGISTRATION;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.ResponseStatus;
import com.storefront.model.User;
import com.storefront.service.IUserManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@Controller
@RequestMapping
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value(PROPERTY_KEY_ACP_SUCCESS_USER_REGISTRATION)
	private String successUserRegistration;

	@Autowired
	private IUserManagement iUserManagement;

	/**
	 * This method fetches all the user list
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/users")
	public @ResponseBody ResponseEntity<List<User>> getAllUsers() throws StoreFrontException {
		try {
			return new ResponseEntity<List<User>>(iUserManagement.findAll(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching user list: " + exception);
			return null;
		}
	}

	/**
	 * This method fetches the user details for particular user id
	 * 
	 * @param userId
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/users/{userId}")
	public @ResponseBody ResponseEntity<User> getUserByUserId(@PathVariable String userId) throws StoreFrontException {
		try {
			return new ResponseEntity<User>(iUserManagement.fetchUserByUserId(Long.parseLong(userId)), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching user details for particular userId: " + userId + "\n" + exception);
			return null;
		}
	}

	/**
	 * This method registers the new user upon sign up
	 * 
	 * @param userName,Email
	 * @return
	 * @throws StoreFrontException
	 */
	@PostMapping(value = "/users/signUpNewUser")
	public @ResponseBody ResponseEntity<ResponseStatus> signUpNewUser(@RequestBody Map<String, Object> payload)
			throws StoreFrontException {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			iUserManagement.signUpNewUser((String) payload.get("userName"), (String) payload.get("emailId"));
			responseStatus.setSuccessMessage(successUserRegistration);
			return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while signing up the new user with name: " + (String) payload.get("userName") + "\n"
					+ exception);
			responseStatus.setErrorMessage(exception.getMessage());
			return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method gets the logged in user details
	 * 
	 * @param
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/users/getCurrentUser/{accessToken}")
	public @ResponseBody ResponseEntity<User> getCurrentUser(@PathVariable String accessToken)
			throws StoreFrontException {
		try {
			return new ResponseEntity<User>(iUserManagement.getCurrentUser(accessToken), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching user details of current user: \n" + exception);
			throw new StoreFrontException("Error while getting the current user info: " + exception);
		}
	}

}
