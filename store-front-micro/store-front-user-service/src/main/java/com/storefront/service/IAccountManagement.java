package com.storefront.service;

import java.util.List;

import com.storefront.common.StoreFrontException;
import com.storefront.model.Account;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IAccountManagement {

	List<Account> findAll() throws StoreFrontException;

}
