package com.storefront.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Account;
import com.storefront.service.IAccountManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@Controller
@RequestMapping
public class AccountController {

	private static Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private IAccountManagement iAccountManagement;

	/**
	 * This method fetches all the account list
	 * 
	 * @return
	 * @throws StoreFrontException
	 */
	@GetMapping(value = "/accounts")
	public @ResponseBody ResponseEntity<List<Account>> getAllAccounts() throws StoreFrontException {
		try {
			return new ResponseEntity<List<Account>>(iAccountManagement.findAll(), HttpStatus.OK);
		} catch (Exception exception) {
			logger.info("Error while fetching account list: " + exception);
			return null;
		}
	}

}
