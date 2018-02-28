package com.storefront.repository;

import org.springframework.data.repository.CrudRepository;

import com.storefront.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {

	public User findByUserName(String userName);

	public User findByUserId(Long userId);

}
