package com.storefront.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.batch.model.User;

/**
 * @author Praneeth.dodedu
 *
 */
public interface IUserReository extends CrudRepository<User, Long> {

	public User findByUserName(String userName);

	public User findByUserId(Long userId);

}
