package com.storefront.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Account;
import com.storefront.repository.IAccountRepository;
import com.storefront.service.IAccountManagement;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class AccountManagementImpl implements IAccountManagement {

	public static final Logger logger = LoggerFactory.getLogger(AccountManagementImpl.class);

	@Autowired
	private IAccountRepository iAccountRepository;

	@Override
	public List<Account> findAll() throws StoreFrontException {
		return (List<Account>) iAccountRepository.findAll();
	}

}
