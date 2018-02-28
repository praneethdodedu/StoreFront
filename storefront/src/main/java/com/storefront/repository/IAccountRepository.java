package com.storefront.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.model.Account;

public interface IAccountRepository extends CrudRepository<Account, Long> {

}
